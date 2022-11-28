package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.entity.WfProcdefType;
import com.netwisd.base.wf.mapper.WfProcdefTypeMapper;
import com.netwisd.base.wf.service.procdef.WfProcdefTypeService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfProcdefTypeDto;
import com.netwisd.base.wf.vo.WfProcdefTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@Service
@Slf4j
public class WfProcdefTypeServiceImpl extends ServiceImpl<WfProcdefTypeMapper, WfProcdefType> implements WfProcdefTypeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcdefTypeMapper wfProcdefTypeMapper;

    /**
    * 单表简单查询操作
    * @param wfProcdefTypeDto
    * @return
    */
    @Override
    public Page list(WfProcdefTypeDto wfProcdefTypeDto) {
        log.debug("流程分类查询参数:"+ wfProcdefTypeDto.toString());
        LambdaQueryWrapper<WfProcdefType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ObjectUtil.isNotEmpty(wfProcdefTypeDto.getParentId()), WfProcdefType::getParentId, wfProcdefTypeDto.getParentId())
                .like(StrUtil.isNotEmpty(wfProcdefTypeDto.getProcdefTypeName()), WfProcdefType::getProcdefTypeName, wfProcdefTypeDto.getProcdefTypeName());
        Page<WfProcdefType> page = wfProcdefTypeMapper.selectPage(wfProcdefTypeDto.getPage(),queryWrapper);
        Page<WfProcdefTypeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfProcdefTypeVo.class);
        log.debug("流程分类查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfProcdefTypeDto
    * @return
    */
    @Override
    public List<WfProcdefTypeVo> lists(WfProcdefTypeDto wfProcdefTypeDto) {
        log.debug("流程分类查询参数:"+ wfProcdefTypeDto.toString());
        LambdaQueryWrapper<WfProcdefType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ObjectUtil.isNotEmpty(wfProcdefTypeDto.getParentId()), WfProcdefType::getParentId, wfProcdefTypeDto.getParentId())
                .like(StrUtil.isNotEmpty(wfProcdefTypeDto.getProcdefTypeName()), WfProcdefType::getProcdefTypeName, wfProcdefTypeDto.getProcdefTypeName());
        List<WfProcdefType> list = wfProcdefTypeMapper.selectList(queryWrapper);
        return handleKids(list);
    }

    /**
     * 处理子级
     * @param list
     */
    List<WfProcdefTypeVo> handleKids(List<WfProcdefType> list){
        //如果是条件查询，组装一下数据
        List<WfProcdefTypeVo> result = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            List<WfProcdefTypeVo> wfProcdefTypeVos = DozerUtils.mapList(dozerMapper, list, WfProcdefTypeVo.class);
            //构建一个map结构，为了比较方便
            Map<Long,WfProcdefTypeVo> map = wfProcdefTypeVos.stream().collect(Collectors.toMap(WfProcdefTypeVo::getId, Function.identity(),(key1, key2) -> key2));
            //遍历结果集，处理数据
            for (WfProcdefTypeVo vo : wfProcdefTypeVos){
                Long parentId = vo.getParentId();
                WfProcdefTypeVo parentObj = map.get(parentId);
                //如果从map中找到其父级，那么就把父级的list里加上自己
                if(ObjectUtil.isNotEmpty(parentObj)){
                    //加上自己，因为是引用，所以getKids再add后，引用数据就加入进去了；
                    parentObj.getKids().add(vo);
                    //从map里把自己干掉，下次不再处理自己——因为列表排序是按level倒序排的，前面的处理的都是最末级层的
                    map.remove(vo.getId());
                }
            }
            //最后整合完的map里，就是我们要的结果
            for (Map.Entry<Long,WfProcdefTypeVo> entry:map.entrySet()){
                result.add(entry.getValue());
            }
        }
        return result;
    }




    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfProcdefTypeVo get(Long id) {
        WfProcdefType wfProcdefType = super.getById(id);
        WfProcdefTypeVo wfProcdefTypeVo = null;
        if(wfProcdefType !=null){
            wfProcdefTypeVo = dozerMapper.map(wfProcdefType,WfProcdefTypeVo.class);
        }
        log.debug("查询成功");
        return wfProcdefTypeVo;
    }

    /**
    * 保存实体
    * @param wfProcdefTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfProcdefTypeDto wfProcdefTypeDto) {
        WfProcdefType wfProcdefType = dozerMapper.map(wfProcdefTypeDto,WfProcdefType.class);
        boolean result = super.save(wfProcdefType);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfProcdefTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfProcdefTypeDto wfProcdefTypeDto) {
        WfProcdefType wfProcdefType = dozerMapper.map(wfProcdefTypeDto,WfProcdefType.class);
        boolean result = super.updateById(wfProcdefType);
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
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
            }
            return result;
        } else {
            throw new IncloudException("ID不能为空！");
        }
    }

    @Override
    public List<WfProcdefTypeVo> selectBatchIds(List<Long> ids) {
        log.debug("按照多个按钮id查询 参数：" + ids.toString());
        List wfProcdefTypeVoList = wfProcdefTypeMapper.selectBatchIds(ids);
        log.debug("按照多个按钮id查询 结果：" + wfProcdefTypeVoList.toString());
        return wfProcdefTypeVoList;
    }
}
