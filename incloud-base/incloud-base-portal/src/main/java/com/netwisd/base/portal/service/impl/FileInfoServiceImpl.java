package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.dto.FileInfoDto;
import com.netwisd.base.portal.entity.FileInfo;
import com.netwisd.base.portal.mapper.FileInfoMapper;
import com.netwisd.base.portal.vo.FileInfoVo;
import com.netwisd.base.portal.service.FileInfoService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 文件存储  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-14 09:51:13
 */
@Service
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
    * 单表简单查询操作
    * @param fileInfoDto
    * @return
    */
    @Override
    public Page list(FileInfoDto fileInfoDto) {
        FileInfo fileInfo = dozerMapper.map(fileInfoDto,FileInfo.class);
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(fileInfo);
        Page<FileInfo> page = fileInfoMapper.selectPage(fileInfoDto.getPage(),queryWrapper);
        Page<FileInfoVo> pageVo = DozerUtils.mapPage(dozerMapper, page, FileInfoVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param fileInfoDto
    * @return
    */
    @Override
    public Page lists(FileInfoDto fileInfoDto) {
        Page<FileInfoVo> pageVo = fileInfoMapper.getPageList(fileInfoDto.getPage(),fileInfoDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public FileInfoVo get(Long id) {
        FileInfo fileInfo = super.getById(id);
        FileInfoVo fileInfoVo = null;
        if(fileInfo !=null){
            fileInfoVo = dozerMapper.map(fileInfo,FileInfoVo.class);
        }
        log.debug("查询成功");
        return fileInfoVo;
    }

    /**
    * 保存实体
    * @param fileInfoDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(FileInfoDto fileInfoDto) {
        FileInfo fileInfo = dozerMapper.map(fileInfoDto,FileInfo.class);
        boolean result = super.save(fileInfo);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param fileInfoDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(FileInfoDto fileInfoDto) {
        FileInfo fileInfo = dozerMapper.map(fileInfoDto,FileInfo.class);
        boolean result = super.updateById(fileInfo);
        if(result){
            log.debug("修改成功");
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
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public void delByBizId(Long bizId) {
        log.debug("按照业务Id删除附件信息。参数：" + bizId);
        if(ObjectUtil.isNull(bizId)) {
            throw new IncloudException("业务Id,不能为空！");
        }
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileInfo::getBizId,bizId);
        int line = fileInfoMapper.delete(queryWrapper);
        log.debug("按照业务Id删除附件信息。受影响行数：" + line);
    }
//通过业务id查
    @Override
    public List<FileInfoVo> getByBizId(Long bizId) {
        log.debug("按照业务Id获取附件信息。参数：" + bizId);
        if(ObjectUtil.isNull(bizId)) {
            throw new IncloudException("业务Id,不能为空！");
        }
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileInfo::getBizId,bizId);
        List<FileInfo> list = fileInfoMapper.selectList(queryWrapper);
        List<FileInfoVo> listVo = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, FileInfoVo.class);
        }
        log.debug("按照业务Id获取附件信息。受影响行数：" + listVo.size());
        return listVo;
    }
}
