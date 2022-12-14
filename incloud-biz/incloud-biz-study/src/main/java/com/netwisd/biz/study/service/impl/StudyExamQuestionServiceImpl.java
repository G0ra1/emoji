package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.QuestionCode;
import com.netwisd.biz.study.constants.QuestionGrade;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.dto.StudyExamQuestionAnswerDto;
import com.netwisd.biz.study.entity.StudyExamQuestion;
import com.netwisd.biz.study.entity.StudyExamQuestionAnswer;
import com.netwisd.biz.study.mapper.StudyExamPaperQuestionMapper;
import com.netwisd.biz.study.mapper.StudyExamQuestionAnswerMapper;
import com.netwisd.biz.study.mapper.StudyExamQuestionMapper;
import com.netwisd.biz.study.service.StudyExamQuestionAnswerService;
import com.netwisd.biz.study.service.StudyExamQuestionService;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamQuestionDto;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description ???????????? ????????????...
 * @author ???????????? sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
@Service
@Slf4j
public class StudyExamQuestionServiceImpl extends ServiceImpl<StudyExamQuestionMapper, StudyExamQuestion> implements StudyExamQuestionService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamQuestionMapper studyExamQuestionMapper;

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    @Autowired
    private StudyExamQuestionAnswerService studyExamQuestionAnswerService;

    @Autowired
    private StudyExamQuestionService studyExamQuestionService;

    /**
    * ????????????????????????
    * @param studyExamQuestionDto
    * @return
    */
    @Override
    public Page list(StudyExamQuestionDto studyExamQuestionDto) {

        LambdaQueryWrapper<StudyExamQuestion> queryWrapper = new LambdaQueryWrapper<>();
        //????????????????????????????????????????????????
            queryWrapper.eq(studyExamQuestionDto.getDatabaseId() != 0,StudyExamQuestion::getDatabaseId, studyExamQuestionDto.getDatabaseId());
            queryWrapper.eq(studyExamQuestionDto.getQuestionCode() != null,StudyExamQuestion::getQuestionCode, studyExamQuestionDto.getQuestionCode());
            queryWrapper.eq(studyExamQuestionDto.getGrade() != null,StudyExamQuestion::getGrade, studyExamQuestionDto.getGrade());
            queryWrapper.eq(studyExamQuestionDto.getIsQuote() != null,StudyExamQuestion::getIsQuote, studyExamQuestionDto.getIsQuote());
            queryWrapper.eq(studyExamQuestionDto.getParse() != null,StudyExamQuestion::getParse, studyExamQuestionDto.getParse());
            queryWrapper.eq(studyExamQuestionDto.getCreateUserName() != null,StudyExamQuestion::getCreateUserName, studyExamQuestionDto.getCreateUserName());
        queryWrapper.like(StringUtils.isNotBlank(studyExamQuestionDto.getQuestion()), StudyExamQuestion::getQuestion, studyExamQuestionDto.getQuestion());
        queryWrapper.orderByDesc(StudyExamQuestion::getCreateTime);
        Page<StudyExamQuestion> page = studyExamQuestionMapper.selectPage(studyExamQuestionDto.getPage(), queryWrapper);
        Page<StudyExamQuestionVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamQuestionVo.class);
        List<StudyExamQuestionVo> records = pageVo.getRecords();
        for (StudyExamQuestionVo studyExamQuestionDefVo : records) {
            LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(StudyExamQuestionAnswer::getQuestionId, studyExamQuestionDefVo.getId());
            List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerMapper.selectList(queryWrapper1);
            List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers, StudyExamQuestionAnswerVo.class);
            studyExamQuestionDefVo.setAnswers(studyExamQuestionAnswerVos);
        }
        log.debug("????????????:" + pageVo.getTotal());
        return pageVo;
    }

    /**
    * ??????????????????
    * @param studyExamQuestionDto
    * @return
    */
    @Override
    public Page lists(StudyExamQuestionDto studyExamQuestionDto) {
        Page<StudyExamQuestionVo> pageVo = studyExamQuestionMapper.getPageList(studyExamQuestionDto.getPage(), studyExamQuestionDto);
        List<StudyExamQuestionVo> records = pageVo.getRecords();
        List<Long> idList = records.stream().map(StudyExamQuestionVo::getId).collect(Collectors.toList());
        List<StudyExamQuestionAnswer> answer = studyExamQuestionAnswerMapper.selectList(Wrappers.<StudyExamQuestionAnswer>lambdaQuery().in(StudyExamQuestionAnswer::getQuestionId, idList));
        Map<Long, List<StudyExamQuestionAnswer>> map = answer.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswer::getQuestionId));
        for (StudyExamQuestionVo studyExamQuestionDefVo : records) {
            List<StudyExamQuestionAnswer> answers = map.get(studyExamQuestionDefVo.getId());
            studyExamQuestionDefVo.setAnswers(answers);
        }
        log.debug("????????????:" + pageVo.getTotal());
        return pageVo;
    }

    /**
    * ??????ID????????????
    * @param id
    * @return
    */
    @Override
    public StudyExamQuestionVo get(Long id) {
        StudyExamQuestion studyExamQuestion = super.getById(id);
        StudyExamQuestionVo studyExamQuestionVo = null;
        if(studyExamQuestion !=null){
            studyExamQuestionVo = dozerMapper.map(studyExamQuestion,StudyExamQuestionVo.class);
        }
        return studyExamQuestionVo;
    }
    /**
     * ????????????????????????
     * @param studyExamQuestionDto
     * @return
     */
    public void checkData (StudyExamQuestionDto studyExamQuestionDto) {
     if (null==studyExamQuestionDto.getQuestion()||studyExamQuestionDto.getQuestion().equals("")){
         throw new IncloudException("?????????????????????");
     }
     if (null==studyExamQuestionDto.getGrade()){
         throw new IncloudException("?????????????????????");
     }
     //?????????
     if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)){
         List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
             if (answers.size()==1){
                 throw new IncloudException("????????????????????????????????????");
             }
         for (StudyExamQuestionAnswerDto answerDto : answers){
             if (answerDto.getAnswer()==null||answerDto.getAnswer().equals("")){
                 throw new IncloudException("??????????????????");
             }
         }
         List<StudyExamQuestionAnswerDto> isTrue = answers.stream().filter(answer -> answer.getIsTrue().equals(YesNo.YES.code)).collect(Collectors.toList());
         if (CollectionUtil.isEmpty(isTrue)){
             throw new IncloudException("?????????????????????");
         }else {
             String answer = isTrue.get(0).getAnswer();
             if (answer==null||answer.equals("")){
                 throw new IncloudException("?????????????????????????????????");
             }
         }
     }
     //?????????
        if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)){
            List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
                if (answers.size()==1 || answers.size()==2){
                    throw new IncloudException("????????????????????????????????????");
                }
            for (StudyExamQuestionAnswerDto answerDto : answers){
                if (answerDto.getAnswer()==null||answerDto.getAnswer().equals("")){
                    throw new IncloudException("??????????????????");
                }
            }
            List<StudyExamQuestionAnswerDto> isTrue = answers.stream().filter(answer -> answer.getIsTrue().equals(YesNo.YES.code)).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(isTrue)){
                throw new IncloudException("?????????????????????");
            }else {
                if (isTrue.size()==1){
                    throw new IncloudException("??????????????????????????????????????????");
                }
                for (StudyExamQuestionAnswerDto answerDto : isTrue){
                    if (answerDto.getAnswer()==null|| answerDto.getAnswer().equals("")){
                        throw new IncloudException("???????????????????????????");
                    }
                }
            }
        }
     //?????????????????????
     if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.COMPLETION.code)
        ||studyExamQuestionDto.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)){
         List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
         for (StudyExamQuestionAnswerDto answerDto :answers)  {
              if (null==answerDto.getAnswer()||answerDto.getAnswer().equals("")){
                  throw new IncloudException("?????????????????????");
              }
         }
     }
    }
    /**
    * ????????????
    * @param studyExamQuestionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyExamQuestionDto studyExamQuestionDto) {
        checkData(studyExamQuestionDto);
        List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
        if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)||
            studyExamQuestionDto.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)){
            List<String> answer = answers.stream().map(StudyExamQuestionAnswerDto::getAnswer).collect(Collectors.toList());
            long count = answer.stream().distinct().count();
            if (answer.size()!=count){
                throw new IncloudException("??????????????????,????????????????????????,???????????????");
            }
        }
            for (StudyExamQuestionAnswerDto studyExamQuestionDef : answers) {
                studyExamQuestionDef.setQuestionId(studyExamQuestionDto.getId());
            }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("??????????????????"));
        studyExamQuestionDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyExamQuestion studyExamQuestionDef = dozerMapper.map(studyExamQuestionDto, StudyExamQuestion.class);
        studyExamQuestionDef.setIsQuote(YesNo.NO.code);
        boolean result = super.save(studyExamQuestionDef);
        List<StudyExamQuestionAnswer> studyExamQuestionAnswers = DozerUtils.mapList(dozerMapper, answers, StudyExamQuestionAnswer.class);
        //???????????????????????????isTrue(??????????????????)??????????????????answer??????????????????,??????????????????????????????
        if(studyExamQuestionDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
            StudyExamQuestionAnswerDto answer = studyExamQuestionDto.getAnswers().get(0);
            if (answer.getIsTrue().equals(YesNo.YES.code)){
                studyExamQuestionAnswers.get(0).setAnswer("???");
            }else {
                studyExamQuestionAnswers.get(0).setAnswer("???");
            }
        }
        studyExamQuestionAnswerService.saveBatch(studyExamQuestionAnswers);
        return result;
    }

    /**
     * ??????????????????
     * @param studyExamQuestionDefDtoList
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<StudyExamQuestionDto> studyExamQuestionDefDtoList) {
        List<StudyExamQuestionAnswerDto> studyExamQuestionAnswerDtoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyExamQuestionDefDtoList)) {
               for (StudyExamQuestionDto studyExamQuestionDefDto : studyExamQuestionDefDtoList) {
                if (studyExamQuestionDefDto.getQuestion() != null) {
                    if (studyExamQuestionDefDto.getAnswers().get(0).getAnswer() == null
                            ||studyExamQuestionDefDto.getAnswers().get(0).getIsTrue() == null
                            || studyExamQuestionDefDto.getQuestionCode() == null
                            || studyExamQuestionDefDto.getGrade() == null) {
                        throw new IncloudException("??????" + studyExamQuestionDefDto.getQuestion() + "????????????");
                    }
                }
                   List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDefDto.getAnswers();
                   for (StudyExamQuestionAnswerDto studyExamQuestionAnswerDto : answers){
                        if (studyExamQuestionDefDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
                            if (studyExamQuestionAnswerDto.getIsTrue().equals(YesNo.YES.code)){
                                studyExamQuestionAnswerDto.setAnswer("???");
                            }else {
                                studyExamQuestionAnswerDto.setAnswer("???");
                            }
                        }
                       studyExamQuestionAnswerDto.setQuestionId(studyExamQuestionDefDto.getId());
                   }
                   studyExamQuestionAnswerDtoList.addAll(answers);
            }
            List<StudyExamQuestion> questions = DozerUtils.mapList(dozerMapper, studyExamQuestionDefDtoList, StudyExamQuestion.class);

            //?????????????????????
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (ObjectUtils.isEmpty(loginAppUser)){
                throw new IncloudException("??????????????????");
            }
            //?????????????????????????????????????????????,????????????,?????????
            questions.forEach(studyExamQuestion -> {
                studyExamQuestion.setIsQuote(YesNo.NO.code);
                studyExamQuestion.setCreateTime(LocalDateTime.now());
                studyExamQuestion.setUpdateTime(LocalDateTime.now());
                studyExamQuestion.setCreateUserName(loginAppUser.getUserName());
                studyExamQuestion.setCreateUserId(loginAppUser.getId());
            }
            );
            studyExamQuestionService.saveBatch(questions);
            List<StudyExamQuestionAnswer> studyExamQuestionAnswers = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswerDtoList, StudyExamQuestionAnswer.class);
            studyExamQuestionAnswers.forEach(answer -> {
                        answer.setCreateTime(LocalDateTime.now());
                        answer.setUpdateTime(LocalDateTime.now());
                        answer.setCreateUserName(loginAppUser.getUserName());
                        answer.setCreateUserId(loginAppUser.getId());
            }
            );
            studyExamQuestionAnswerService.saveBatch(studyExamQuestionAnswers);
        }
    }
    /**
    * ????????????
    * @param studyExamQuestionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyExamQuestionDto studyExamQuestionDto) {
        checkData(studyExamQuestionDto);
        studyExamQuestionDto.setUpdateTime(LocalDateTime.now());
        StudyExamQuestion studyExamQuestion = dozerMapper.map(studyExamQuestionDto,StudyExamQuestion.class);
        boolean result = super.updateById(studyExamQuestion);
        if (result) {
            LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StudyExamQuestionAnswer::getQuestionId,studyExamQuestionDto.getId());
            List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
            for (StudyExamQuestionAnswerDto studyExamQuestionAnswers : answers) {
                studyExamQuestionAnswers.setQuestionId(studyExamQuestionDto.getId());
                studyExamQuestionAnswers.setUpdateTime(LocalDateTime.now());
            }
            List<StudyExamQuestionAnswer> studyExamQuestionAnswers = DozerUtils.mapList(dozerMapper, answers, StudyExamQuestionAnswer.class);
            studyExamQuestionAnswerService.saveOrUpdateOrDeleteBatch(queryWrapper,studyExamQuestionAnswers);
        }
        return result;
    }

    /**
    * ??????ID??????
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        StudyExamQuestion studyExamQuestion = studyExamQuestionMapper.selectOne(Wrappers.<StudyExamQuestion>lambdaQuery().eq(StudyExamQuestion::getId, id));
        if (studyExamQuestion.getIsQuote().equals(YesNo.YES.code)){
            throw new IncloudException("?????????????????????????????????,????????????");
          }
        LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamQuestionAnswer::getQuestionId, id);
        List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerMapper.selectList(queryWrapper);
        List<Long> collect1 = studyExamQuestionAnswers.stream().map(StudyExamQuestionAnswer::getId).collect(Collectors.toList());
        studyExamQuestionAnswerService.removeByIds(collect1);
        return super.removeById(id);
    }

    /**
     * ??????excel?????????????????????
     * @param file
     * @return
     */
    @Override
    public List<StudyExamQuestionVo> readExcel(MultipartFile file) {
        try {
            //????????????????????????????????????Excel????????????Workbook??????
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            //??????Excel???????????????????????????
            Sheet sht0 = xssfWorkbook.getSheetAt(0);
            //???Sheet???????????????????????????
            List<StudyExamQuestionVo> returnList = new ArrayList<>();
            for (Row r : sht0) {
                StudyExamQuestionVo studyExamQuestionVo = new StudyExamQuestionVo();
                //??????
                if ("?????????".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.SINGLE_CHOICE.code);
                }
                if ("?????????".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.MULTIPLE_CHOICE.code);
                }
                if ("?????????".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.COMPLETION.code);
                }
                if ("?????????".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.JUDGMENT.code);
                }
                if ("?????????".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.SHORT_ANSWER.code);
                }
                if (ObjectUtils.isEmpty(studyExamQuestionVo.getQuestionCode())) {
                    continue;
                }
                //??????
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(1).getStringCellValue())) {
                    studyExamQuestionVo.setQuestion(r.getCell(1).getStringCellValue());
                }
                //????????????
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(2).getStringCellValue())) {
                    studyExamQuestionVo.setParse(r.getCell(2).getStringCellValue());
                }
                //??????
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(3).getStringCellValue())) {
                    if ("???".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.EASY.code);
                    }
                    if ("???".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.MODERATE.code);
                    }
                    if ("???".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.HARD.code);
                    }
                }
                //??????
                List<StudyExamQuestionAnswerVo> answerVos = new ArrayList<>();
                String rightKey = r.getCell(4).getStringCellValue();
                char[] chars = rightKey.toCharArray();
                for (char s : chars) {
                    Character.UnicodeBlock ub = Character.UnicodeBlock.of(s);
                    if (ub == Character.UnicodeBlock.VERTICAL_FORMS
                            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                    ) {
                        rightKey = rightKey.replaceAll("???", ",");
                        break;
                    }
                }
                //????????????????????????
                if (QuestionCode.SINGLE_CHOICE.code.equals(studyExamQuestionVo.getQuestionCode()) || QuestionCode.MULTIPLE_CHOICE.code.equals(studyExamQuestionVo.getQuestionCode())) {
                    List<Integer> rightKeyList = new ArrayList<>();
                    String[] split = rightKey.split(",");
                    List<String> strings = Arrays.asList(split);
                    strings.forEach(string->{
                        if (string.equals("A")) {
                            rightKeyList.add(0);
                        }
                        if (string.equals("B")){
                            rightKeyList.add(1);
                        }
                        if (string.equals("C")) {
                            rightKeyList.add(2);
                        }
                        if (string.equals("D")) {
                            rightKeyList.add(3);
                        }
                        if (string.equals("E")) {
                            rightKeyList.add(4);
                        }
                        if (string.equals("F")) {
                            rightKeyList.add(5);
                        }
                    });
                    //???????????????????????????????????????
                    for (int i = 5 ,j = 0; ; i ++,j ++){
                        StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                        try {

                          if( r.getCell(i).getCellType().equals(CellType.STRING)){
                            String answer = r.getCell(i).getStringCellValue();
                            answerVo.setAnswer(answer);
                            answerVo.setSort(j);
                            answerVo.setIsTrue(0);
                          }
                          if (r.getCell(i).getCellType().equals(CellType.NUMERIC)){
                              String format = new DecimalFormat().format(r.getCell(i).getNumericCellValue());
                              answerVo.setAnswer(format);
                              answerVo.setSort(j);
                              answerVo.setIsTrue(0);

                          }
                            rightKeyList.forEach(right->{
                                if (right.equals(answerVo.getSort())) {
                                    answerVo.setIsTrue(YesNo.YES.code);
                                }
                            });

                            answerVos.add(answerVo);
                        }catch (Exception e){
                            log.debug(String.valueOf(e));
                            break;
                        }
                    }
                }
                //?????????
                if (QuestionCode.JUDGMENT.code.equals(studyExamQuestionVo.getQuestionCode())) {
                    StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                    if (StringUtils.isNotEmpty(rightKey)) {
                        answerVo.setAnswer(rightKey);
                        if ("??????".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("??????".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        if ("???".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("???".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        if ("???".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("???".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        answerVo.setSort(0);
                    }else{
                        answerVos.add(answerVo);
                    }

                    answerVos.add(answerVo);
                }
                //?????????
                if (QuestionCode.COMPLETION.code.equals(studyExamQuestionVo.getQuestionCode())) {
                    if (com.netwisd.base.common.util.StringUtils.isNotEmpty(rightKey)) {
                        List<String> rightKeyList = Arrays.asList(rightKey.split(","));
                        for (int i = 0 ; i < rightKeyList.size() ; i ++){
                            String right = rightKeyList.get(i);
                            StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                            answerVo.setAnswer(right);
                            answerVo.setIsTrue(YesNo.YES.code);
                            answerVo.setSort(i);
                            answerVos.add(answerVo);
                        }
                    }else{
                        StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                        answerVos.add(answerVo);
                    }
                }
                //?????????
                if (QuestionCode.SHORT_ANSWER.code.equals(studyExamQuestionVo.getQuestionCode())){
                    if (com.netwisd.base.common.util.StringUtils.isNotEmpty(rightKey)){
                        StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                        answerVo.setAnswer(rightKey);
                        answerVo.setIsTrue(YesNo.YES.code);
                        answerVo.setSort(0);
                        answerVos.add(answerVo);
                    }else{
                        StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                        answerVos.add(answerVo);
                    }
                }
                //????????????????????????
                if (CollectionUtils.isNotEmpty(answerVos)) {
                    studyExamQuestionVo.setAnswers(answerVos);
                }
                log.debug("?????????{}",studyExamQuestionVo);
                returnList.add(studyExamQuestionVo);
            }
            return returnList;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
