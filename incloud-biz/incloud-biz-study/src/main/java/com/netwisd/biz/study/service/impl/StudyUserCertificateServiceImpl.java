package com.netwisd.biz.study.service.impl;

import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.util.URLUtil;
import com.aspose.words.Document;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.dto.StudyUserCertificateDto;
import com.netwisd.biz.study.entity.StudyCertificateTemplate;
import com.netwisd.biz.study.entity.StudySpecial;
import com.netwisd.biz.study.entity.StudySpecialJieye;
import com.netwisd.biz.study.entity.StudyUserCertificate;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyCertificateTemplateMapper;
import com.netwisd.biz.study.mapper.StudySpecialJieyeMapper;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.mapper.StudyUserCertificateMapper;
import com.netwisd.biz.study.service.StudyUserCertificateService;
import com.netwisd.biz.study.util.Docx2PdfUtils;
import com.netwisd.biz.study.vo.StudyUserCertificateVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ???????????? lhy@netwisd.com
 * @Description ??????????????? ????????????...
 * @date 2022-04-25 09:39:13
 */
@Service
@Slf4j
public class StudyUserCertificateServiceImpl extends ServiceImpl<StudyUserCertificateMapper, StudyUserCertificate> implements StudyUserCertificateService {

    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserCertificateMapper studyUserCertificateMapper;

    @Autowired
    private StudyCertificateTemplateMapper studyCertificateTemplateMapper;

    @Autowired
    private StudySpecialMapper studySpecialMapper;

    @Autowired
    private StudySpecialJieyeMapper studySpecialJieyeMapper;

    @Autowired
    private MdmClient mdmClient;

    /**
     * ????????????????????????
     *
     * @param infoDto
     * @return
     */
    @Override
    public Page pageList(StudyUserCertificateDto infoDto) {
        LambdaQueryWrapper<StudyUserCertificate> queryWrapper = Wrappers.<StudyUserCertificate>lambdaQuery().like(StringUtils.isNotBlank(infoDto.getCertificateName()), StudyUserCertificate::getCertificateName, infoDto.getCertificateName()).eq(StringUtils.isNotBlank(infoDto.getTypeCode()), StudyUserCertificate::getTypeCode, infoDto.getTypeCode()).eq(infoDto.getUserId() != null, StudyUserCertificate::getUserId, infoDto.getUserId()).eq(infoDto.getSpecialId() != null, StudyUserCertificate::getSpecialId, infoDto.getSpecialId()).eq(infoDto.getExamPaperId() != null, StudyUserCertificate::getExamPaperId, infoDto.getExamPaperId()).like(StringUtils.isNotBlank(infoDto.getTypeName()), StudyUserCertificate::getTypeName, infoDto.getTypeName()).like(StringUtils.isNotBlank(infoDto.getUserName()), StudyUserCertificate::getUserName, infoDto.getUserName()).like(StringUtils.isNotBlank(infoDto.getIdcard()), StudyUserCertificate::getIdcard, infoDto.getIdcard()).like(StringUtils.isNotBlank(infoDto.getExamLevel()), StudyUserCertificate::getExamLevel, infoDto.getExamLevel()).orderByDesc(StudyUserCertificate::getCreateTime);
        Page<StudyUserCertificate> page = studyUserCertificateMapper.selectPage(infoDto.getPage(), queryWrapper);
        Page<StudyUserCertificateVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyUserCertificateVo.class);
        log.debug("????????????:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * ??????ID????????????
     *
     * @param id
     * @return
     */
    @Override
    public StudyUserCertificateVo detail(Long id) {
        StudyUserCertificate studyUserCertificate = super.getById(id);
        StudyUserCertificateVo studyUserCertificateVo = null;
        if (studyUserCertificate != null) {
            studyUserCertificateVo = dozerMapper.map(studyUserCertificate, StudyUserCertificateVo.class);
        }
        log.debug("????????????");
        return studyUserCertificateVo;
    }

    /**
     * ??????????????????
     *
     * @param userId
     * @param specialId
     * @param examScore
     */
    @Override
    @Transactional
    public void createUserCertificate(Long userId, Long specialId, BigDecimal examScore) {
        log.info("?????????????????????userId={},specialId={},examScore={}", userId, specialId, examScore);
        StudySpecial special = studySpecialMapper.selectById(specialId);
        //???????????????????????????????????????
        if (special.getSpecialPaperId() != null) {
            //????????????????????????????????????
            LambdaQueryWrapper<StudySpecialJieye> specialJieyeWrapper = Wrappers.<StudySpecialJieye>lambdaQuery().eq(StudySpecialJieye::getSpecialId, specialId).isNotNull(StudySpecialJieye::getCertificateId).ge(StudySpecialJieye::getExamHighestScore, examScore).le(StudySpecialJieye::getExamLowestScore, examScore);
            List<StudySpecialJieye> specialJieyeList = studySpecialJieyeMapper.selectList(specialJieyeWrapper);
            if (CollectionUtils.isNotEmpty(specialJieyeList)) {
                List<StudyUserCertificate> userCertificateList = new ArrayList<>();
                //????????????ID??????????????????
                LoginAppUser appUser = AppUserUtil.getLoginAppUser();
                for (StudySpecialJieye specialJieye : specialJieyeList) {
                    StudyUserCertificate userCertificate = new StudyUserCertificate();
                    userCertificate.setId(IDENTIFIER_GENERATOR.nextId(this).longValue());
                    userCertificate.setCreateTime(LocalDateTime.now());
                    userCertificate.setCreateUserId(appUser.getId());
                    userCertificate.setCreateUserName(appUser.getUsername());
                    //??????????????????
                    StudyCertificateTemplate certificateTemplate = studyCertificateTemplateMapper.selectById(specialJieye.getCertificateId());
                    userCertificate.setCertificateTemplateId(certificateTemplate.getId());
                    userCertificate.setCertificateName(certificateTemplate.getCertificateName());
                    userCertificate.setCertificateCode(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS")));
                    userCertificate.setTypeCode(certificateTemplate.getTypeCode());
                    userCertificate.setTypeName(certificateTemplate.getTypeName());
                    userCertificate.setIssuer(certificateTemplate.getIssuer());
                    userCertificate.setInstructions(certificateTemplate.getInstructions());
                    userCertificate.setIssueDate(LocalDate.now());
                    userCertificate.setValidity(certificateTemplate.getValidity());
                    userCertificate.setTemplateFilePath(certificateTemplate.getFileUrl());
                    //??????????????????
                    userCertificate.setSpecialId(specialId);
                    userCertificate.setSpecialName(special.getSpecialName());
                    //??????????????????
                    userCertificate.setExamPaperId(special.getSpecialPaperId());
                    userCertificate.setExamPaperName(special.getSpecialPaperName());
                    userCertificate.setExamScore(examScore);
                    userCertificate.setExamLevel(specialJieye.getExamRank());
                    //??????????????????
                    Result<MdmUserVo> resultUser = mdmClient.getUserInfoById(userId);
                    MdmUserVo userInfo = Optional.ofNullable(resultUser.getData()).orElseGet(() -> new MdmUserVo());
                    userCertificate.setUserId(userId);
                    userCertificate.setUserName(userInfo.getUserName());
                    userCertificate.setIdcard(userInfo.getIdCard());
                    //??????????????????
                    userCertificateList.add(userCertificate);
                }
                log.info("????????????{}?????????", userCertificateList.size());
                this.saveBatch(userCertificateList);
            }
        }


    }

    /**
     * ???????????????
     *
     * @param id
     * @param response
     * @param request
     */
    @Override
    @SneakyThrows
    public void stream(Long id, HttpServletResponse response, HttpServletRequest request) {
        log.info("??????????????????:id={}", id);
        StudyUserCertificate info = Optional.ofNullable(super.getById(id)).orElseThrow(() -> new IncloudException("???????????????"));
        String templateFilePath = Optional.ofNullable(info.getTemplateFilePath()).orElseThrow(() -> new IncloudException("??????????????????"));
        /*File templateFile = new File(templateFilePath);
        if (!templateFile.exists()) {
            throw new IncloudException("??????????????????");
        }*/
        //????????????
        Map<String, Object> dataMap = new HashMap<>();
        //????????????
        dataMap.put("CERTIFICATE_NAME", info.getCertificateName());
        //????????????
        dataMap.put("CERTIFICATE_CODE", info.getCertificateCode());
        //????????????
        dataMap.put("SPECIAL_NAME", info.getSpecialName());
        //??????
        dataMap.put("FULL_NAME", info.getUserName());
        //????????????
        dataMap.put("IDCARD", info.getIdcard());
        //????????????
        dataMap.put("ISSUER", info.getIssuer());
        //??????
        dataMap.put("LEVEL", info.getExamLevel());

        //??????????????????
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy???MM???dd???");
        //??????????????????
        dataMap.put("ISSUE_TIME", info.getIssueDate().format(dateTimeFormatter));
        //??????????????????
        if (info.getValidity() != null && info.getValidity() > 0) {
            dataMap.put("VALIDITY_TIME", info.getIssueDate().plusYears(info.getValidity()).format(dateTimeFormatter));
        } else {
            dataMap.put("VALIDITY_TIME", "??????");
        }
        //????????????????????????????????????
        // ???????????????docx????????????????????????
        XWPFTemplate template = XWPFTemplate.compile(new UrlResource(URLUtil.url(templateFilePath)).getStream()).render(dataMap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //?????????????????????word?????????byteArrayOutputStream
        template.write(byteArrayOutputStream);

        //??????aspose ??????
        Docx2PdfUtils.getAsposeLicense();// ??????License ???????????????????????????pdf??????????????????
        Document doc = new Document(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        String previewFileName = info.getCertificateName() + ".pdf";
        String userAgent = request.getHeader("User-Agent");
        //????????????MSIE IE 8 ??? IE 10 ,Trident/7.0:IE 11
        if (userAgent.toUpperCase().contains("MSIE") || userAgent.contains("Trident/7.0")) {
            previewFileName = java.net.URLEncoder.encode(previewFileName, "UTF-8");
        } else {
            previewFileName = new String(previewFileName.getBytes("UTF-8"), "iso-8859-1");
        }
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", previewFileName));
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        try {
            long startTime = System.currentTimeMillis();
            //???????????????response???????????????
            doc.save(response.getOutputStream(), com.aspose.words.SaveFormat.PDF);//????????????DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF ????????????
            long endTime = System.currentTimeMillis();
            log.info("use aspose word turn to pdf token???{}s.", (endTime - startTime) / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("aspose??????????????????");
        } finally {
            if (template != null) {
                template.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getUserCertificateNum(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            userId = loginAppUser.getId();
        }
        LambdaQueryWrapper<StudyUserCertificate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUserCertificate::getUserId, userId);
        return studyUserCertificateMapper.selectCount(queryWrapper);
    }
}
