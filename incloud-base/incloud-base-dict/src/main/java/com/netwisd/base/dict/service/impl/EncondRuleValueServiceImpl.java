package com.netwisd.base.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.dto.EncondRuleValueDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.base.common.dict.vo.EncondRuleValueVo;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.dict.entity.*;
import com.netwisd.base.dict.mapper.EncondRuleDetailMapper;
import com.netwisd.base.dict.mapper.EncondRuleMapper;
import com.netwisd.base.dict.mapper.EncondRuleValueMapper;
import com.netwisd.base.dict.service.EncondRuleService;
import com.netwisd.base.dict.service.EncondRuleValueService;
import com.netwisd.common.core.exception.IncloudException;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 编码规则值 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:29
 */
@Service
@Slf4j
public class EncondRuleValueServiceImpl extends ServiceImpl<EncondRuleValueMapper, EncondRuleValue> implements EncondRuleValueService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private EncondRuleValueMapper encondRuleValueMapper;

    @Autowired
    private EncondRuleMapper encondRuleMapper;

    @Autowired
    private EncondRuleDetailMapper encondRuleDetailMapper;

    @Autowired
    private EncondRuleService encondRuleService;

    /**
    * 单表简单查询操作
    * @param encondRuleValueDto
    * @return
    */
    @Override
    public Page list(EncondRuleValueDto encondRuleValueDto) {
        LambdaQueryWrapper<EncondRuleValue> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<EncondRuleValue> page = encondRuleValueMapper.selectPage(encondRuleValueDto.getPage(),queryWrapper);
        Page<EncondRuleValueVo> pageVo = DozerUtils.mapPage(dozerMapper, page, EncondRuleValueVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param encondRuleValueDto
    * @return
    */
    @Override
    public Page lists(EncondRuleValueDto encondRuleValueDto) {
        Page<EncondRuleValueVo> pageVo = encondRuleValueMapper.getPageList(encondRuleValueDto.getPage(),encondRuleValueDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public EncondRuleValueVo get(Long id) {
        EncondRuleValue encondRuleValue = super.getById(id);
        EncondRuleValueVo encondRuleValueVo = null;
        if(encondRuleValue !=null){
            encondRuleValueVo = dozerMapper.map(encondRuleValue,EncondRuleValueVo.class);
        }
        log.debug("查询成功");
        return encondRuleValueVo;
    }

    /**
    * 保存实体
    * @param encondRuleValueDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(EncondRuleValueDto encondRuleValueDto) {
        EncondRuleValue encondRuleValue = dozerMapper.map(encondRuleValueDto,EncondRuleValue.class);
        boolean result = super.save(encondRuleValue);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param encondRuleValueDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(EncondRuleValueDto encondRuleValueDto) {
        encondRuleValueDto.setUpdateTime(LocalDateTime.now());
        EncondRuleValue encondRuleValue = dozerMapper.map(encondRuleValueDto,EncondRuleValue.class);
        boolean result = super.updateById(encondRuleValue);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
//        boolean result = super.removeById(id);
//        if(result){
//            log.debug("删除成功");
//        }
//        return result;
        return removeByIds(Arrays.stream(ids.split(",")).collect(Collectors.toList()));
    }

    @Override
    public String creatEncondValue(EncondRuleDto encondRuleDto) {
        //1 业务表单需要传递 规则ID 表单Map字段值 　
        String ruleType = encondRuleDto.getRuleType();
        //String encondField = encondRuleDto.getEncondField();
        Long ruleId = encondRuleDto.getId();
        Map<String,Object> formMap = encondRuleDto.getFormMap();
        //如果是 业务表单用 需要传递规则ID 跟表单字段formMap
        EncondRuleVo ruleVo = new EncondRuleVo();
        if(StringUtils.isNotEmpty(ruleType)&&(ruleType.equals("2")||ruleType.equals("3"))){
            LambdaQueryWrapper<EncondRule> queryWrapperRule = new LambdaQueryWrapper<>();
            queryWrapperRule.eq(EncondRule::getRuleType,ruleType);
            List ruleVoList = encondRuleMapper.selectList(queryWrapperRule);
            if(CollectionUtil.isNotEmpty(ruleVoList)){
                ruleVo = dozerMapper.map(ruleVoList.get(0),EncondRuleVo.class);
                //子表
                LambdaQueryWrapper<EncondRuleDetail> queryWrapperRuleDetail = new LambdaQueryWrapper<>();
                queryWrapperRuleDetail.eq(EncondRuleDetail::getRuleId,ruleVo.getId());
                List<EncondRuleDetail> ruleDetailList = encondRuleDetailMapper.selectList(queryWrapperRuleDetail);
                List<EncondRuleDetailVo> ruleDetailVoList = DozerUtils.mapList(dozerMapper,ruleDetailList,EncondRuleDetailVo.class);
                ruleVo.setEncondRuleDetailList(ruleDetailVoList);
            }
        }else{
            if(ruleId==0L||ruleId==null){
                throw new IncloudException("未选择规则ID");
            }
            ruleVo =  encondRuleService.get(ruleId);
        }
        String resultEncondValue = "";
        List<EncondRuleDetailVo> detailList = ruleVo.getEncondRuleDetailList();
        //初始值
        String initValue = null;
        //判断 自动生成的 周期
        String cycle = null;
        if(ObjectUtil.isNotEmpty(detailList) && !detailList.isEmpty()){
            for (EncondRuleDetailVo vo: detailList) {
                if(StringUtils.isNotEmpty(vo.getInitialValue())){
                    initValue = vo.getInitialValue();
                    break;
                }
            }
            for (EncondRuleDetailVo vo: detailList) {
                if(StringUtils.isNotEmpty(vo.getResetCycle())&&vo.getResetCycle().equals("不重置")){
                    cycle = "no";
                    break;
                }
                if(StringUtils.isNotEmpty(vo.getResetCycle())&&vo.getResetCycle().equals("每天重置(凌晨12点)")){
                    cycle = "day";
                    break;
                }
                if(StringUtils.isNotEmpty(vo.getResetCycle())&&vo.getResetCycle().equals("每周重置(凌晨12点)")){
                    cycle = "week";
                    break;
                }
                if(StringUtils.isNotEmpty(vo.getResetCycle())&&vo.getResetCycle().equals("每月重置(凌晨12点)")){
                    cycle = "month";
                    break;
                }
                if(StringUtils.isNotEmpty(vo.getResetCycle())&&vo.getResetCycle().equals("每年重置(凌晨12点)")){
                    cycle = "year";
                    break;
                }
            }
        }
        //先判断该规则是否已经有记录存在 有就更新 没有就直接生成
        LambdaQueryWrapper<EncondRuleValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(ruleVo.getId()),EncondRuleValue::getRuleId,ruleVo.getId());
        queryWrapper.orderByDesc(EncondRuleValue::getSort);
        List<EncondRuleValue> valueList = this.list(queryWrapper);
        //如果不存在编码值
        if(valueList.isEmpty()){
            StringBuffer encondValue = new StringBuffer();
            if(ObjectUtil.isNotEmpty(detailList)&&!detailList.isEmpty()){
                for (int i = 0; i <detailList.size() ; i++) {
                    EncondRuleDetailVo detailVo = detailList.get(i);
                    //编号方式
                    String encondType = detailVo.getEncondType();
                    //内容
                    String content = detailVo.getContent();
                    //格式化 内容值中的 “-”
                    content = content.replace("-","");
                    //重置周期
                    String resetCycle = detailVo.getResetCycle();
                    //初始值
                    String initialValue = detailVo.getInitialValue();
                    //初始值
                    if("固定字符".equals(encondType)){
                        encondValue.append(content);
                        encondValue.append("-");
                    }
                    if("表单字段".equals(encondType)){
                        Object formValue = formMap.get(content);
                        if(ObjectUtil.isEmpty(formValue)){
                            encondValue.append("${f."+content+"}");
                            encondValue.append("-");
                        }else{
                            encondValue.append(formValue);
                            encondValue.append("-");
                        }

                    }
                    Date date=new Date();
                    DateFormat format=new SimpleDateFormat("yyyyMMdd");
                    String dateNew = format.format(date);
                    String yy = String.valueOf(dateNew).substring(0,4);
                    String y = String.valueOf(dateNew).substring(2,4);
                    String m = String.valueOf(dateNew).substring(4,6);
                    String d = String.valueOf(dateNew).substring(6,8);
                    if("系统时间".equals(encondType)){
                        if("年(yy)".equals(content)){
                            encondValue.append(y);
                            encondValue.append("-");
                        }else if("年月(yyMM)".equals(content)){
                            encondValue.append(y);
                            encondValue.append(m);
                            encondValue.append("-");
                        }else if("年月日(yyMMdd)".equals(content)){
                            encondValue.append(y);
                            encondValue.append(m);
                            encondValue.append(d);
                            encondValue.append("-");
                        }else if("年(yyyy)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append("-");
                        }else if("年月(yyyyMM)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append(m);
                            encondValue.append("-");
                        }else if("年月日(yyyyMMdd)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append(m);
                            encondValue.append(d);
                            encondValue.append("-");
                        }

                    }
                    if("UUID".equals(encondType)){
                        encondValue.append(UUID.randomUUID().toString().replaceAll("-",""));
                        encondValue.append("-");
                    }
                    if("自动计数".equals(encondType)){
                        if(initialValue.length()>Integer.valueOf(content)){
                            throw new IncloudException("初始值位数不能大于总位数");
                        }
                        for (int j = 0; j <Integer.valueOf(content)-initialValue.length() ; j++) {
                            encondValue.append("0");
                        }
                        encondValue.append(initialValue);
                        encondValue.append("-");
                    }

                }
                //存入规则值记录表 去除最后一个"-"
                resultEncondValue =  encondValue.toString();
                resultEncondValue= resultEncondValue.substring(0,resultEncondValue.length()-1);
                EncondRuleValue value = new EncondRuleValue();
                value.setId(IdGenerator.getIdGenerator());
                value.setRuleId(ruleVo.getId());
                value.setRuleName(ruleVo.getRuleName());
                value.setValue(resultEncondValue);
                value.setFormName(ruleVo.getFormName());
                value.setRuleType(ruleVo.getRuleType());
                value.setSort(1);
                this.save(value);
                return resultEncondValue;
            }
        }else{
            //该规则最新的一条记录值
            String newValue = valueList.get(0).getValue();
            int sortOld = valueList.get(0).getSort();
            log.info("sortOld===={}",sortOld);
            //该规则最新的一条记录值的创建时间
            LocalDateTime creatDate = valueList.get(0).getCreateTime();
            //存在记录 更新
            StringBuffer encondValue = new StringBuffer();
            if(ObjectUtil.isNotEmpty(detailList)){
                for (int i = 0; i <detailList.size() ; i++) {
                    EncondRuleDetailVo detailVo = detailList.get(i);
                    //编号方式
                    String encondType = detailVo.getEncondType();
                    //内容
                    String content = detailVo.getContent();
                    content = content.replace("-","");
                    //重置周期
                    String resetCycle = detailVo.getResetCycle();
                    //初始值
                    String initialValue = detailVo.getInitialValue();
                    //排序位置
                    int sort = detailVo.getSort();
                    //初始值
                    if("固定字符".equals(encondType)){
                        encondValue.append(content);
                        encondValue.append("-");
                    }
                    if("表单字段".equals(encondType)){
                        Object formValue = formMap.get(content);
                        if(ObjectUtil.isEmpty(formValue)){
                            encondValue.append("${f."+content+"}");
                            encondValue.append("-");
                        }else{
                            encondValue.append(formValue);
                            encondValue.append("-");
                        }
                    }
                    Date date=new Date();
                    DateFormat format=new SimpleDateFormat("yyyyMMdd");
                    String dateNew = format.format(date);
                    String yy = String.valueOf(dateNew).substring(0,4);
                    String y = String.valueOf(dateNew).substring(2,4);
                    String m = String.valueOf(dateNew).substring(4,6);
                    String d = String.valueOf(dateNew).substring(6,8);
                    if("系统时间".equals(encondType)){
                        if("年(yy)".equals(content)){
                            encondValue.append(y);
                            encondValue.append("-");
                        }else if("年月(yyMM)".equals(content)){
                            encondValue.append(y);
                            encondValue.append(m);
                            encondValue.append("-");
                        }else if("年月日(yyMMdd)".equals(content)){
                            encondValue.append(y);
                            encondValue.append(m);
                            encondValue.append(d);
                            encondValue.append("-");
                        }else if("年(yyyy)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append("-");
                        }else if("年月(yyyyMM)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append(m);
                            encondValue.append("-");
                        }else if("年月日(yyyyMMdd)".equals(content)){
                            encondValue.append(yy);
                            encondValue.append(m);
                            encondValue.append(d);
                            encondValue.append("-");
                        }

                    }
                    if("UUID".equals(encondType)){
                        encondValue.append(UUID.randomUUID().toString().replaceAll("-",""));
                        encondValue.append("-");
                    }
                    if("自动计数".equals(encondType)){
                        //先获取到该规则最新的一次记录数据中的 自动计数
                        for(int l = 0; l < sort-1; l++){
                            newValue = newValue.substring(newValue.indexOf("-")+1);
                        }
                        if(newValue.indexOf("-")>0){
                            newValue = newValue.substring(0,newValue.indexOf("-"));
                        }
                        //格式化 计数位数
                        StringBuffer encondValueNew = new StringBuffer();
                        for (int j = 0; j <Integer.valueOf(content) ; j++) {
                            encondValueNew.append("0");
                        }
                        //不重置
                        if(StringUtils.isNotEmpty(cycle)&&cycle.equals("no")){
                            DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                            String newJs = df.format(Long.valueOf(newValue) + 1);
                            encondValue.append(newJs);
                        //按天重置
                        }else if(StringUtils.isNotEmpty(cycle)&&cycle.equals("day")){
                            //如果创建时间跟当前时间是同一天 累加+1
                            if(isToday(localDateTimeToDate(creatDate))){
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(newValue) + 1);
                                encondValue.append(newJs);
                             //如果过了周期 重新生成 自动计数
                            }else {
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(initValue));
                                encondValue.append(newJs);
                            }

                         //按周重置
                        }else if(StringUtils.isNotEmpty(cycle)&&cycle.equals("week")){
                            //如果创建时间跟当前时间是同一天 累加+1
                            if(isThisWeek(localDateTimeToDate(creatDate))){
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(newValue) + 1);
                                encondValue.append(newJs);
                                //如果过了周期 重新生成 自动计数
                            }else {
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(initValue));
                                encondValue.append(newJs);
                            }
                         //按月重置
                        }else if(StringUtils.isNotEmpty(cycle)&&cycle.equals("month")){
                            //如果创建时间跟当前时间是同一天 累加+1
                            if(isThisMonth(localDateTimeToDate(creatDate))){
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(newValue) + 1);
                                encondValue.append(newJs);
                                //如果过了周期 重新生成 自动计数
                            }else {
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(initValue));
                                encondValue.append(newJs);
                            }
                        //按年重置
                        }else if(StringUtils.isNotEmpty(cycle)&&cycle.equals("year")){
                            //如果创建时间跟当前时间是同一天 累加+1
                            if(isThisYear(localDateTimeToDate(creatDate))){
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(newValue) + 1);
                                encondValue.append(newJs);
                                //如果过了周期 重新生成 自动计数
                            }else {
                                DecimalFormat df = new DecimalFormat(encondValueNew.toString());
                                String newJs = df.format(Long.valueOf(initValue));
                                encondValue.append(newJs);
                            }
                        }else{
                            throw new IncloudException("未设置重置周期");
                        }
                        encondValue.append("-");
                    }

                }
                sortOld++;
                //存入规则值记录表 去除最后一个"-"
                resultEncondValue =  encondValue.toString();
                resultEncondValue= resultEncondValue.substring(0,resultEncondValue.length()-1);
                EncondRuleValue value = new EncondRuleValue();
                value.setId(IdGenerator.getIdGenerator());
                value.setRuleId(ruleVo.getId());
                value.setFormName(ruleVo.getFormName());
                value.setRuleName(ruleVo.getRuleName());
                value.setValue(resultEncondValue);
                value.setRuleType(ruleVo.getRuleType());
                value.setSort(sortOld);
                this.save(value);
                return resultEncondValue;
            }

        }
        return resultEncondValue;
    }

    @Override
    public Boolean deleteEncondValue(String formName, String value, String ruleType) {
        LambdaQueryWrapper<EncondRuleValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(formName), EncondRuleValue::getFormName,formName);
        queryWrapper.eq(StrUtil.isNotEmpty(value), EncondRuleValue::getValue,value);
        queryWrapper.eq(StrUtil.isNotEmpty(ruleType), EncondRuleValue::getRuleType,ruleType);
        boolean result = remove(queryWrapper);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public EncondRuleVo getRuleDetail(String formName,String ruleType) {
        if(StringUtils.isEmpty(formName)){
            throw new IncloudException("未选择业务表单名称!");
        }
        if(StringUtils.isEmpty(ruleType)){
            throw new IncloudException("未选择规则类型!");
        }
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(formName),EncondRule::getFormName,formName);
        queryWrapper.eq(StrUtil.isNotEmpty(ruleType),EncondRule::getRuleType,ruleType);
        List<EncondRule> valueList = encondRuleMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(valueList)){
            EncondRule rule = valueList.get(0);
            LambdaQueryWrapper<EncondRuleDetail> queryDetailWrapper = new LambdaQueryWrapper<>();
            queryDetailWrapper.eq(EncondRuleDetail::getRuleId,rule.getId());
            List<EncondRuleDetail> detailList = encondRuleDetailMapper.selectList(queryDetailWrapper);
            List<EncondRuleDetailVo> detailVoList = new ArrayList<>();
            for (EncondRuleDetail detail :detailList) {
                if(detail.getEncondType().equals("表单字段")){
                    detailVoList.add(dozerMapper.map(detail,EncondRuleDetailVo.class));
                }
            }
            EncondRuleVo ruleVo = dozerMapper.map(rule,EncondRuleVo.class);
            ruleVo.setEncondRuleDetailList(detailVoList);
            return  ruleVo;

        }else{
            throw new IncloudException("未查询到该表单规则!");
        }
    }

    @Override
    public String getRuleValueForqd(String formName,String encondField) {
//        Map<String, String> rusultMap = new HashMap<>();
        if(StringUtils.isEmpty(formName)){
            //throw new IncloudException("未选择业务表单名称!");
            return "";
        }
        if(StringUtils.isEmpty(encondField)){
            //throw new IncloudException("未选择规则值字段!");
            return "";
        }
//        if(entityMap.isEmpty()){
//            throw new IncloudException("业务map为空!");
//        }
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(formName),EncondRule::getFormName,formName);
        queryWrapper.eq(EncondRule::getRuleType,"1");
        queryWrapper.eq(StrUtil.isNotEmpty(encondField),EncondRule::getEncondField,encondField);
        List<EncondRule> valueList = encondRuleMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(valueList)){
            EncondRule rule = valueList.get(0);
            LambdaQueryWrapper<EncondRuleDetail> queryDetailWrapper = new LambdaQueryWrapper<>();
            queryDetailWrapper.eq(EncondRuleDetail::getRuleId,rule.getId());
            List<EncondRuleDetail> detailList = encondRuleDetailMapper.selectList(queryDetailWrapper);
            List<EncondRuleDetailVo> detailVoList = new ArrayList<>();
            //详情不为空
            if(CollectionUtil.isNotEmpty(detailList)){
                for (EncondRuleDetail detail :detailList) {
                    if(detail.getEncondType().equals("表单字段")){
                        detailVoList.add(dozerMapper.map(detail,EncondRuleDetailVo.class));
                    }
                }
            }else{
                //throw new IncloudException("未查询到该表单规则详情!");
                log.info("没有查询到该规则详情！{}",formName,encondField);
                return "";
            }
            EncondRuleVo ruleVo = dozerMapper.map(rule,EncondRuleVo.class);
            ruleVo.setEncondRuleDetailList(detailVoList);
            String ruleValue = "";
            //如果是保存前生成
            EncondRuleDto encondRule = new EncondRuleDto();
            if(!StringUtils.isEmpty(ruleVo.getCreateOpportunity())&&ruleVo.getCreateOpportunity().equals("1")){
                Map<String,Object> mapFrom = new HashMap<>();
//                if(CollectionUtil.isNotEmpty(detailVoList)){
//                    detailVoList.forEach(EncondRuleDetailVo->{
//                        mapFrom.put(EncondRuleDetailVo.getContent(),entityMap.get(EncondRuleDetailVo.getContent()));
//                    });
//                }
                //创建规则号
                encondRule.setId(ruleVo.getId());
                encondRule.setFormMap(mapFrom);
                ruleValue = this.creatEncondValue(encondRule);
                log.info("规则值为---{}",ruleValue);
            }else{
                log.info("该规则是表单保存时生成！");
            }
//            rusultMap.put(rule.getEncondField(),ruleValue);
            return ruleValue;
        }else{
            //throw new IncloudException("未查询到该表单规则!");
            log.info("没有查询到该规则！{}",formName,encondField);
            return "";
        }
    }

    @Override
    public String createWfValue(String camundaProcdefId) {
        //根据流程实例Id生成规则值
        if(StringUtils.isEmpty(camundaProcdefId)){
            //throw new IncloudException("未选择流程实例ID");
            return "";
        }
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EncondRule::getCamundaProcdefId,camundaProcdefId);
        List<EncondRule> ruleList = encondRuleMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(ruleList)){
            //throw new IncloudException("未查询到该流程实例规则！");
            return "";
        }
        EncondRuleVo ruleVo = dozerMapper.map(ruleList.get(0),EncondRuleVo.class);
        //子表
        LambdaQueryWrapper<EncondRuleDetail> queryWrapperRuleDetail = new LambdaQueryWrapper<>();
        queryWrapperRuleDetail.eq(EncondRuleDetail::getRuleId,ruleVo.getId());
        List<EncondRuleDetail> ruleDetailList = encondRuleDetailMapper.selectList(queryWrapperRuleDetail);
        List<EncondRuleDetailVo> detailList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(ruleDetailList)){
            ruleDetailList.forEach(EncondRuleDetail -> {
                detailList.add(dozerMapper.map(EncondRuleDetail,EncondRuleDetailVo.class));
            });
            ruleVo.setEncondRuleDetailList(detailList);
        }else{
            //throw new IncloudException("未查询到该流程实例规则详情！");
            return "";
        }
        String resultEncondValue = "";
        StringBuffer encondValue = new StringBuffer();
        if(ObjectUtil.isNotEmpty(detailList)&&!detailList.isEmpty()){
            for (int i = 0; i <detailList.size() ; i++) {
                EncondRuleDetailVo detailVo = detailList.get(i);
                //编号方式
                String encondType = detailVo.getEncondType();
                //内容
                String content = detailVo.getContent();
                //格式化 内容值中的 “-”
                content = content.replace("-","");
                String formName = detailVo.getFormName();
                String formType = detailVo.getFormType();
                if("固定字符".equals(encondType)){
                    encondValue.append(content);
                    encondValue.append("-");
                }
                if("表单字段".equals(encondType)){
                    //业务表单
                    if(formType.equals("1")){
                        encondValue.append("${f."+formName+"."+content+"}");
                        encondValue.append("-");
                    }else if(formType.equals("2")){
                        encondValue.append("${f."+"wf"+"."+content+"}");
                        encondValue.append("-");
                    }
                }
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyyMMdd");
                String dateNew = format.format(date);
                String yy = String.valueOf(dateNew).substring(0,4);
                String y = String.valueOf(dateNew).substring(2,4);
                String m = String.valueOf(dateNew).substring(4,6);
                String d = String.valueOf(dateNew).substring(6,8);
                if("系统时间".equals(encondType)){
                    if("年(yy)".equals(content)){
                        encondValue.append(y);
                        encondValue.append("-");
                    }else if("年月(yyMM)".equals(content)){
                        encondValue.append(y);
                        encondValue.append(m);
                        encondValue.append("-");
                    }else if("年月日(yyMMdd)".equals(content)){
                        encondValue.append(y);
                        encondValue.append(m);
                        encondValue.append(d);
                        encondValue.append("-");
                    }else if("年(yyyy)".equals(content)){
                        encondValue.append(yy);
                        encondValue.append("-");
                    }else if("年月(yyyyMM)".equals(content)){
                        encondValue.append(yy);
                        encondValue.append(m);
                        encondValue.append("-");
                    }else if("年月日(yyyyMMdd)".equals(content)){
                        encondValue.append(yy);
                        encondValue.append(m);
                        encondValue.append(d);
                        encondValue.append("-");
                    }

                }
                if("UUID".equals(encondType)){
                    encondValue.append(UUID.randomUUID().toString().replaceAll("-",""));
                    encondValue.append("-");
                }

            }
            //存入规则值记录表 去除最后一个"-"
            resultEncondValue =  encondValue.toString();
            resultEncondValue= resultEncondValue.substring(0,resultEncondValue.length()-1);
            EncondRuleValue value = new EncondRuleValue();
            value.setId(IdGenerator.getIdGenerator());
            value.setRuleId(ruleVo.getId());
            value.setRuleName(ruleVo.getRuleName());
            value.setValue(resultEncondValue);
            //业务表单
            if(ruleVo.getRuleType().equals("1")){
                value.setFormName(ruleVo.getFormName());
            }else if(ruleVo.getRuleType().equals("2")){
                value.setCamundaProcdefName(ruleVo.getCamundaProcdefName());
            }
            value.setRuleType(ruleVo.getRuleType());

            this.save(value);
            return resultEncondValue;
        }
        return resultEncondValue;

    }


    @Override
    public String getRuleValue(String formName,String encondField,Map<String, Object> entityMap) {
        //Map<String, String> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(formName)){
            //throw new IncloudException("未选择业务表单名称!");
            return "";
        }
        if(StringUtils.isEmpty(encondField)){
            //throw new IncloudException("未选择业务规则值字段!");
            return "";
        }
        if(entityMap.isEmpty()){
            //throw new IncloudException("业务map为空!");
            return "";
        }
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(formName),EncondRule::getFormName,formName);
        queryWrapper.eq(StrUtil.isNotEmpty(encondField),EncondRule::getEncondField,encondField);
        queryWrapper.eq(EncondRule::getRuleType,"1");
        List<EncondRule> valueList = encondRuleMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(valueList)){
            EncondRule rule = valueList.get(0);
            LambdaQueryWrapper<EncondRuleDetail> queryDetailWrapper = new LambdaQueryWrapper<>();
            queryDetailWrapper.eq(EncondRuleDetail::getRuleId,rule.getId());
            List<EncondRuleDetail> detailList = encondRuleDetailMapper.selectList(queryDetailWrapper);
            List<EncondRuleDetailVo> detailVoList = new ArrayList<>();
            //详情不为空
            if(CollectionUtil.isNotEmpty(detailList)){
                for (EncondRuleDetail detail :detailList) {
                    if(detail.getEncondType().equals("表单字段")){
                        detailVoList.add(dozerMapper.map(detail,EncondRuleDetailVo.class));
                    }
                }
            }else{
                //throw new IncloudException("未查询到该表单规则详情!");
                log.info("没有查询到该规则详情！{}",formName,encondField);
                return "";
            }
            EncondRuleVo ruleVo = dozerMapper.map(rule,EncondRuleVo.class);
            ruleVo.setEncondRuleDetailList(detailVoList);
            String ruleValue = "";
            //如果是保存后生成的
            EncondRuleDto encondRule = new EncondRuleDto();
            if(!StringUtils.isEmpty(ruleVo.getCreateOpportunity())&&ruleVo.getCreateOpportunity().equals("2")){
                Map<String,Object> mapFrom = new HashMap<>();
                if(CollectionUtil.isNotEmpty(detailVoList)){
                    detailVoList.forEach(EncondRuleDetailVo->{
                        mapFrom.put(EncondRuleDetailVo.getContent(),entityMap.get(EncondRuleDetailVo.getContent()));
                    });
                }
                //创建规则号
                encondRule.setId(ruleVo.getId());
                encondRule.setFormMap(mapFrom);
                ruleValue = this.creatEncondValue(encondRule);
                log.info("规则值为---{}",ruleValue);
            }else{
                log.info("该规则是表单保存前生成！");
            }
//            if(rule.getEncondField()!=null){
//                resultMap.put(rule.getEncondField(),ruleValue);
//            }
            return ruleValue;
        }else{
            //throw new IncloudException("未查询到该表单规则!");
            log.info("没有查询到该规则！{}",formName,encondField);
            return "";
        }
    }


    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    //判断选择的日期是否是本年
    public static boolean isThisYear(Date time) {
        return isThisTime(time, "yyyy");
    }

    private static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }


    /**
     * LocalDateTime转Date
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            return Date.from(zdt.toInstant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
      * 根据属性名获取属性值
      *
      * @param fieldName
      * @param object
      * @return
      */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            return (String)field.get(object);
        } catch(Exception e){
            return null;
        }
    }

}
