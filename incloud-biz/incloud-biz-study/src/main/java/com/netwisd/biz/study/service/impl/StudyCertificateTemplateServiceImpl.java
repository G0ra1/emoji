package com.netwisd.biz.study.service.impl;

import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.util.URLUtil;
import com.aspose.words.Document;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.dto.StudyCertificateTemplateDto;
import com.netwisd.biz.study.entity.StudyCertificateTemplate;
import com.netwisd.biz.study.entity.StudySpecialJieye;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyCertificateTemplateMapper;
import com.netwisd.biz.study.mapper.StudySpecialJieyeMapper;
import com.netwisd.biz.study.service.StudyCertificateTemplateService;
import com.netwisd.biz.study.util.Docx2PdfUtils;
import com.netwisd.biz.study.vo.StudyCertificateTemplateVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 证书表 功能描述...
 * @date 2022-04-19 19:31:09
 */
@Service
@Slf4j
public class StudyCertificateTemplateServiceImpl extends ServiceImpl<StudyCertificateTemplateMapper, StudyCertificateTemplate> implements StudyCertificateTemplateService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyCertificateTemplateMapper studyCertificateTemplateMapper;

    @Autowired
    private StudySpecialJieyeMapper studySpecialJieyeMapper;
    @Autowired
    private MdmClient mdmClient;//主数据client

    /**
     * 分页查询
     *
     * @param studyCertificateTemplateDto
     * @return
     */
    @Override
    public Page pageList(StudyCertificateTemplateDto studyCertificateTemplateDto) {
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        Integer studyUserRole = mdmClient.getStudyUserRole(appUser.getId());//获取当前登陆人的角色
        LambdaQueryWrapper<StudyCertificateTemplate> queryWrapper = new LambdaQueryWrapper<>();
        //如果当前登陆人是老师，尽可查看自己上传。
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {//判断是否是老师
            queryWrapper.eq(StudyCertificateTemplate::getCreateUserId, appUser.getId());
        }
        queryWrapper.like(StringUtils.isNotBlank(studyCertificateTemplateDto.getCertificateName()), StudyCertificateTemplate::getCertificateName, studyCertificateTemplateDto.getCertificateName());
        queryWrapper.eq(StringUtils.isNotBlank(studyCertificateTemplateDto.getTypeCode()), StudyCertificateTemplate::getTypeCode, studyCertificateTemplateDto.getTypeCode());
        queryWrapper.eq(studyCertificateTemplateDto.getCreateUserId() != null && !studyCertificateTemplateDto.getCreateUserId().equals(0L), StudyCertificateTemplate::getCreateUserId, studyCertificateTemplateDto.getCreateUserId());
        queryWrapper.like(StringUtils.isNotBlank(studyCertificateTemplateDto.getIssuer()), StudyCertificateTemplate::getIssuer, studyCertificateTemplateDto.getIssuer());
        queryWrapper.orderByDesc(StudyCertificateTemplate::getCreateTime);
        Page<StudyCertificateTemplateVo> pageVo = DozerUtils.mapPage(dozerMapper, studyCertificateTemplateMapper.selectPage(studyCertificateTemplateDto.getPage(), queryWrapper), StudyCertificateTemplateVo.class);
        log.debug("证书模板分页查询成功，查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID获取详情
     *
     * @param id
     * @return
     */
    @Override
    public StudyCertificateTemplateVo get(Long id) {
        return dozerMapper.map(Optional.ofNullable(super.getById(id)).orElse(new StudyCertificateTemplate()), StudyCertificateTemplateVo.class);
    }

    /**
     * 检验必填字段
     */
    private void checkData(StudyCertificateTemplateDto infoDto) {
        if (StringUtils.isBlank(infoDto.getTypeCode()) || StringUtils.isBlank(infoDto.getTypeName())) {
            throw new IncloudException("请选择证书分类");
        }
        if (StringUtils.isBlank(infoDto.getCertificateName())) {
            throw new IncloudException("请填写证书名称");
        }
        if (StringUtils.isBlank(infoDto.getIssuer())) {
            throw new IncloudException("请填写颁发机构");
        }
        if (infoDto.getValidity() == null) {
            throw new IncloudException("请选择证书有效期");
        }
        if (infoDto.getFileId() == null || StringUtils.isBlank(infoDto.getFilePath()) || StringUtils.isBlank(infoDto.getFileUrl())) {
            throw new IncloudException("请上传证书模板");
        }
        String fileSuffix = infoDto.getFilePath().substring(infoDto.getFilePath().lastIndexOf(".") + 1);
        if (StringUtils.isBlank(fileSuffix) || !"docx".equalsIgnoreCase(fileSuffix)) {
            throw new IncloudException("请上传docx文件");
        }
    }

    /**
     * 保存实体
     *
     * @param infoDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(StudyCertificateTemplateDto infoDto) {
        checkData(infoDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        infoDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyCertificateTemplate info = dozerMapper.map(infoDto, StudyCertificateTemplate.class);
        return super.save(info);
    }

    /**
     * 修改实体
     *
     * @param infoDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(StudyCertificateTemplateDto infoDto) {
        this.checkData(infoDto);
        infoDto.setUpdateTime(LocalDateTime.now());
        StudyCertificateTemplate info = dozerMapper.map(infoDto, StudyCertificateTemplate.class);
        return super.updateById(info);
    }

    /**
     * 通过ID删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new IncloudException("请选择要删除的记录"));
        List<Long> idsList = Arrays.stream(ids.split(",")).map(id -> Long.valueOf(id)).collect(Collectors.toList());
        LambdaQueryWrapper<StudyCertificateTemplate> queryWrapper = Wrappers.<StudyCertificateTemplate>lambdaQuery().in(StudyCertificateTemplate::getId, idsList);
        List<StudyCertificateTemplate> infoList = studyCertificateTemplateMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(infoList)) {
            infoList.forEach(info -> {
                LambdaQueryWrapper<StudySpecialJieye> jieyeLambdaQueryWrapper = Wrappers.<StudySpecialJieye>lambdaQuery().eq(StudySpecialJieye::getCertificateId, info.getId());
                if (studySpecialJieyeMapper.selectCount(jieyeLambdaQueryWrapper) > 0) {
                    throw new IncloudException("证书[" + info.getCertificateName() + "]已被专项引用不可删除");
                }
            });
            return studyCertificateTemplateMapper.delete(queryWrapper) > 0;
        }
        return false;
    }

    @Override
    @SneakyThrows
    public void previewTemplate(Long id, HttpServletResponse response, HttpServletRequest request) {
        log.info("开始预览证书模板:id={}", id);
        StudyCertificateTemplate info = Optional.ofNullable(super.getById(id)).orElseThrow(() -> new IncloudException("预览的证书不存在"));
        String templateFileUrl = Optional.ofNullable(info.getFileUrl()).orElseThrow(() -> new IncloudException("证书模板丢失"));
        /*File templateFile = new File(templateFilePath);
        if (!templateFile.exists()) {
            throw new IncloudException("证书模板丢失");
        }

        ResourceUtil.getStream("");*/

        //模板数据
        Map<String, Object> dataMap = new HashMap<>();
        //证书名称
        dataMap.put("CERTIFICATE_NAME", info.getCertificateName());
        //证书编码
        dataMap.put("CERTIFICATE_CODE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS")));
        //专题名称
        dataMap.put("SPECIAL_NAME", "xxxx专题");
        //姓名
        dataMap.put("FULL_NAME", "张三");
        //身份证号
        dataMap.put("IDCARD", "410***********7689");
        //颁发机构
        dataMap.put("ISSUER", info.getIssuer());
        //等级
        dataMap.put("LEVEL", "一等奖");

        //定义日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        //证书颁发时间
        dataMap.put("ISSUE_TIME", LocalDate.now().format(dateTimeFormatter));
        //证书到期时间
        if (info.getValidity() != null && info.getValidity() > 0) {
            dataMap.put("VALIDITY_TIME", LocalDate.now().plusYears(info.getValidity()).format(dateTimeFormatter));
        } else {
            dataMap.put("VALIDITY_TIME", "长期");
        }
        //根据模板解析生成示例证书
        // 根据写好的docx模板创建模板对象
        XWPFTemplate template = XWPFTemplate.compile(new UrlResource(URLUtil.url(templateFileUrl)).getStream()).render(dataMap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //将解析后生成的word输出到byteArrayOutputStream
        template.write(byteArrayOutputStream);

        //验证aspose 授权
        Docx2PdfUtils.getAsposeLicense();// 验证License 若不验证则转化出的pdf文档会有水印
        Document doc = new Document(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        String previewFileName = "证书预览.pdf";
        String userAgent = request.getHeader("User-Agent");
        //解决乱码MSIE IE 8 至 IE 10 ,Trident/7.0:IE 11
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
            //转换文件到response的输出流中
            doc.save(response.getOutputStream(), com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long endTime = System.currentTimeMillis();
            log.info("use aspose word turn to pdf token：{}s.", (endTime - startTime) / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("aspose转换文件失败");
        } finally {
            if (template != null) {
                template.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
    }

    /*public static void main(String[] args) throws IOException {
        //模板数据
        Map<String, Object> dataMap = new HashMap<>();
        //证书名称
        dataMap.put("CERTIFICATE_NAME", "证书名称");
        //证书编码
        dataMap.put("CERTIFICATE_CODE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS")));
        //专题名称
        dataMap.put("SPECIAL_NAME", "xxxx专题");
        //姓名
        dataMap.put("FULL_NAME", "张三");
        //身份证号
        dataMap.put("IDCARD", "410***********7689");
        //颁发机构
        dataMap.put("ISSUER", "云数网讯");
        //等级
        dataMap.put("LEVEL", "一等奖");

        //定义日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        //证书颁发时间
        dataMap.put("ISSUE_TIME", LocalDate.now().format(dateTimeFormatter));
        //证书到期时间
        dataMap.put("VALIDITY_TIME", "长期");

        *//*ConfigureBuilder builder = Configure.newBuilder();
        builder.setElMode(Configure.ELMode.SPEL_MODE);*//*
        // 需要生成的word
        File wordFile = new File("D:/xinshengcheng.docx");
        wordFile.getParentFile().mkdirs();
        // 根据写好的docx模板创建模板对象
        XWPFTemplate template = XWPFTemplate.compile("D:/xin.docx").render(dataMap);
        FileOutputStream out = new FileOutputStream(wordFile);
        // objectDto为数据对象
        //template.render(dataMap);
        template.write(out);
        out.flush();
    }*/
}
