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
 * @Description 题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
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
    * 单表简单查询操作
    * @param studyExamQuestionDto
    * @return
    */
    @Override
    public Page list(StudyExamQuestionDto studyExamQuestionDto) {

        LambdaQueryWrapper<StudyExamQuestion> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
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
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
    * 题目查询分页
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
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
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
     * 校验新增题目数据
     * @param studyExamQuestionDto
     * @return
     */
    public void checkData (StudyExamQuestionDto studyExamQuestionDto) {
     if (null==studyExamQuestionDto.getQuestion()||studyExamQuestionDto.getQuestion().equals("")){
         throw new IncloudException("请填写题干内容");
     }
     if (null==studyExamQuestionDto.getGrade()){
         throw new IncloudException("请选择题目难度");
     }
     //单选题
     if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)){
         List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
             if (answers.size()==1){
                 throw new IncloudException("单选题请至少设置两个选项");
             }
         for (StudyExamQuestionAnswerDto answerDto : answers){
             if (answerDto.getAnswer()==null||answerDto.getAnswer().equals("")){
                 throw new IncloudException("选项内容为空");
             }
         }
         List<StudyExamQuestionAnswerDto> isTrue = answers.stream().filter(answer -> answer.getIsTrue().equals(YesNo.YES.code)).collect(Collectors.toList());
         if (CollectionUtil.isEmpty(isTrue)){
             throw new IncloudException("请设置正确答案");
         }else {
             String answer = isTrue.get(0).getAnswer();
             if (answer==null||answer.equals("")){
                 throw new IncloudException("正确答案选项未填写内容");
             }
         }
     }
     //多选题
        if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)){
            List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
                if (answers.size()==1 || answers.size()==2){
                    throw new IncloudException("多选题请至少设置三个选项");
                }
            for (StudyExamQuestionAnswerDto answerDto : answers){
                if (answerDto.getAnswer()==null||answerDto.getAnswer().equals("")){
                    throw new IncloudException("选项内容为空");
                }
            }
            List<StudyExamQuestionAnswerDto> isTrue = answers.stream().filter(answer -> answer.getIsTrue().equals(YesNo.YES.code)).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(isTrue)){
                throw new IncloudException("请设置正确答案");
            }else {
                if (isTrue.size()==1){
                    throw new IncloudException("多选题请最少设置两个正确答案");
                }
                for (StudyExamQuestionAnswerDto answerDto : isTrue){
                    if (answerDto.getAnswer()==null|| answerDto.getAnswer().equals("")){
                        throw new IncloudException("正确选项未填写内容");
                    }
                }
            }
        }
     //填空题或简答题
     if (studyExamQuestionDto.getQuestionCode().equals(QuestionCode.COMPLETION.code)
        ||studyExamQuestionDto.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)){
         List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDto.getAnswers();
         for (StudyExamQuestionAnswerDto answerDto :answers)  {
              if (null==answerDto.getAnswer()||answerDto.getAnswer().equals("")){
                  throw new IncloudException("请填写答案内容");
              }
         }
     }
    }
    /**
    * 保存实体
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
                throw new IncloudException("选项内容重复,或有多个空白选项,请重新填写");
            }
        }
            for (StudyExamQuestionAnswerDto studyExamQuestionDef : answers) {
                studyExamQuestionDef.setQuestionId(studyExamQuestionDto.getId());
            }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studyExamQuestionDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyExamQuestion studyExamQuestionDef = dozerMapper.map(studyExamQuestionDto, StudyExamQuestion.class);
        studyExamQuestionDef.setIsQuote(YesNo.NO.code);
        boolean result = super.save(studyExamQuestionDef);
        List<StudyExamQuestionAnswer> studyExamQuestionAnswers = DozerUtils.mapList(dozerMapper, answers, StudyExamQuestionAnswer.class);
        //针对判断题根据传的isTrue(是否正确答案)设置题目答案answer为正确或错误,方便后面阅卷对比答案
        if(studyExamQuestionDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
            StudyExamQuestionAnswerDto answer = studyExamQuestionDto.getAnswers().get(0);
            if (answer.getIsTrue().equals(YesNo.YES.code)){
                studyExamQuestionAnswers.get(0).setAnswer("对");
            }else {
                studyExamQuestionAnswers.get(0).setAnswer("错");
            }
        }
        studyExamQuestionAnswerService.saveBatch(studyExamQuestionAnswers);
        return result;
    }

    /**
     * 批量保存题目
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
                        throw new IncloudException("题目" + studyExamQuestionDefDto.getQuestion() + "信息不全");
                    }
                }
                   List<StudyExamQuestionAnswerDto> answers = studyExamQuestionDefDto.getAnswers();
                   for (StudyExamQuestionAnswerDto studyExamQuestionAnswerDto : answers){
                        if (studyExamQuestionDefDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
                            if (studyExamQuestionAnswerDto.getIsTrue().equals(YesNo.YES.code)){
                                studyExamQuestionAnswerDto.setAnswer("对");
                            }else {
                                studyExamQuestionAnswerDto.setAnswer("错");
                            }
                        }
                       studyExamQuestionAnswerDto.setQuestionId(studyExamQuestionDefDto.getId());
                   }
                   studyExamQuestionAnswerDtoList.addAll(answers);
            }
            List<StudyExamQuestion> questions = DozerUtils.mapList(dozerMapper, studyExamQuestionDefDtoList, StudyExamQuestion.class);

            //获取当前登录人
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (ObjectUtils.isEmpty(loginAppUser)){
                throw new IncloudException("请重新登录！");
            }
            //给文档导入的题目设置未引用状态,创建时间,创建人
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
    * 修改实体
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
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        StudyExamQuestion studyExamQuestion = studyExamQuestionMapper.selectOne(Wrappers.<StudyExamQuestion>lambdaQuery().eq(StudyExamQuestion::getId, id));
        if (studyExamQuestion.getIsQuote().equals(YesNo.YES.code)){
            throw new IncloudException("该题目已被选做试卷题目,不能删除");
          }
        LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamQuestionAnswer::getQuestionId, id);
        List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerMapper.selectList(queryWrapper);
        List<Long> collect1 = studyExamQuestionAnswers.stream().map(StudyExamQuestionAnswer::getId).collect(Collectors.toList());
        studyExamQuestionAnswerService.removeByIds(collect1);
        return super.removeById(id);
    }

    /**
     * 读取excel模板文件的内容
     * @param file
     * @return
     */
    @Override
    public List<StudyExamQuestionVo> readExcel(MultipartFile file) {
        try {
            //根据指定的文件输入流导入Excel从而产生Workbook对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            //获取Excel文档中的第一个表单
            Sheet sht0 = xssfWorkbook.getSheetAt(0);
            //对Sheet中的每一行进行迭代
            List<StudyExamQuestionVo> returnList = new ArrayList<>();
            for (Row r : sht0) {
                StudyExamQuestionVo studyExamQuestionVo = new StudyExamQuestionVo();
                //题型
                if ("单选题".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.SINGLE_CHOICE.code);
                }
                if ("多选题".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.MULTIPLE_CHOICE.code);
                }
                if ("填空题".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.COMPLETION.code);
                }
                if ("判断题".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.JUDGMENT.code);
                }
                if ("简答题".equals(r.getCell(0).getStringCellValue())) {
                    studyExamQuestionVo.setQuestionCode(QuestionCode.SHORT_ANSWER.code);
                }
                if (ObjectUtils.isEmpty(studyExamQuestionVo.getQuestionCode())) {
                    continue;
                }
                //题干
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(1).getStringCellValue())) {
                    studyExamQuestionVo.setQuestion(r.getCell(1).getStringCellValue());
                }
                //题目解析
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(2).getStringCellValue())) {
                    studyExamQuestionVo.setParse(r.getCell(2).getStringCellValue());
                }
                //难度
                if (com.netwisd.base.common.util.StringUtils.isNotEmpty(r.getCell(3).getStringCellValue())) {
                    if ("低".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.EASY.code);
                    }
                    if ("中".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.MODERATE.code);
                    }
                    if ("高".equals(r.getCell(3).getStringCellValue())) {
                        studyExamQuestionVo.setGrade(QuestionGrade.HARD.code);
                    }
                }
                //答案
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
                        rightKey = rightKey.replaceAll("，", ",");
                        break;
                    }
                }
                //单选题或者多选题
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
                    //单选题或者多选题的选项处理
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
                //判断题
                if (QuestionCode.JUDGMENT.code.equals(studyExamQuestionVo.getQuestionCode())) {
                    StudyExamQuestionAnswerVo answerVo = new StudyExamQuestionAnswerVo();
                    if (StringUtils.isNotEmpty(rightKey)) {
                        answerVo.setAnswer(rightKey);
                        if ("正确".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("错误".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        if ("对".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("错".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        if ("是".equals(rightKey)) {
                            answerVo.setIsTrue(1);
                        }
                        if ("否".equals(rightKey)) {
                            answerVo.setIsTrue(0);
                        }
                        answerVo.setSort(0);
                    }else{
                        answerVos.add(answerVo);
                    }

                    answerVos.add(answerVo);
                }
                //填空题
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
                //简答题
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
                //将答案放入题目中
                if (CollectionUtils.isNotEmpty(answerVos)) {
                    studyExamQuestionVo.setAnswers(answerVos);
                }
                log.debug("内容：{}",studyExamQuestionVo);
                returnList.add(studyExamQuestionVo);
            }
            return returnList;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
