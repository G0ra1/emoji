package com.netwisd.base.file.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.mapper.FileDsMapper;
import com.netwisd.base.file.service.FileDsService;
import com.netwisd.base.file.service.FileInfoServiceFactory;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.file.dto.FileDsDto;
import com.netwisd.base.file.vo.FileDsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * @Description 文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
@Service
@Slf4j
public class FileDsServiceImpl extends ServiceImpl<FileDsMapper, FileDs> implements FileDsService {
    private static final String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALrLWoXPU/Ex+gogTHQaWXDCPOzIBQUEUZMowguJW4deLiRfS69x31VxRSr8kxSD/cL8ifhlaQhuo3gS8XfltGTf6/6B2XXjKmrYLTaZaD86CGeG0bFSyDytZlWs0H0aNoK0pdDjrb4f8dO1wMpM6wt7qODvpygjZvBsflAW1fYnAgMBAAECgYAQtID6+Ii0SFjpMOxFcdnP2L8kGdtBEJrPA1UfPQB/ga+0twUIwrFLbd7WslHhAtDd8EHSghc7ltFtupv3sgZI26BXLe1EwiPr/vC9ZAi6IKQDDU2AEdYRzIDigfl8XJCGk3woRXNzqQcJafZw36XpT5k0pAMImYG/oCNTF2KrmQJBAP6otIqOP4DnjeJ6Pojswm3GCReyHUMFO8rS2SqZoGEEbKjl6CZJnogpNl+D85//WT7D1YzutZ8U1i8zTBn3DD0CQQC7xym4M9CB8S2j22af6ueIPuNVjBpyHqgxTHbshzdPf5wB7wFZEtUv+RQZT/oYSr4YnnMRqtYq8G505ePEAv4zAkBgER6CgmT8aN3CiSEcIEy8go+di8i0Jr5GpkHcazXwQ24GTSzFfNI8RWfIoot+WSK+pbvivY5wY7jk93IG/YZ1AkBGzW7epLLe/BhQa17Dt6f7iHLhg8VI+GREymchAk8Jq70gQYVJl79Iqms4rB5J4IzS7ZPHupmscSHE9BWwh8xfAkBEqfjRdOb8fryMD17xFQOj4OC3XxhutM7moBGKEUvmhYEmcK2A0sI/HZq6ZZd+hNNaXmmt7quSDhVvgZZ3jKXw";
    private static final String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6y1qFz1PxMfoKIEx0GllwwjzsyAUFBFGTKMILiVuHXi4kX0uvcd9VcUUq/JMUg/3C/In4ZWkIbqN4EvF35bRk3+v+gdl14ypq2C02mWg/OghnhtGxUsg8rWZVrNB9GjaCtKXQ462+H/HTtcDKTOsLe6jg76coI2bwbH5QFtX2JwIDAQAB";

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private FileDsMapper fileDsMapper;

    @Autowired
    FileInfoServiceFactory fileInfoServiceFactory;

    RSA rsa = new RSA(pri, null);

    /**
    * 单表简单查询操作
    * @param fileDsDto
    * @return
    */
    @Override
    public Page list(FileDsDto fileDsDto) {
        LambdaQueryWrapper<FileDs> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<FileDs> page = fileDsMapper.selectPage(fileDsDto.getPage(),queryWrapper);
        Page<FileDsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, FileDsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @return
    */
    @Override
    public List all() {
        List<FileDs> list = this.list();
        List<FileDsVo> listVo = DozerUtils.mapList(dozerMapper, list, FileDsVo.class);
        log.debug("查询条数:"+list.size());
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public FileDsVo get(Long id) {
        FileDs fileDs = super.getById(id);
        FileDsVo fileDsVo = null;
        if(fileDs !=null){
            fileDsVo = dozerMapper.map(fileDs,FileDsVo.class);
        }
        log.debug("查询成功");
        return fileDsVo;
    }

    @Override
    public FileDs getDefaultDs() {
        LambdaQueryWrapper<FileDs> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(FileDs::getIsDefault, YesNo.YES.code.intValue());
        List<FileDs> list = list(queryWrapper);
        FileDs fileDs = null;
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            fileDs = list.get(0);
        }
        return fileDs;
    }

    /**
    * 保存实体
    * @param fileDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(FileDsDto fileDsDto) {
        /**
         * 检验数据是否已经存在，模仿小孙的写法；
         */
        Optional.of(count(Wrappers.<FileDs>lambdaQuery().eq(FileDs::getPoolName, fileDsDto.getPoolName()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("{}已经存在", fileDsDto.getPoolName());
            }
            return true;
        });
        if(ObjectUtil.isNotEmpty(fileDsDto.getIsDefault()) && fileDsDto.getIsDefault().intValue() == YesNo.YES.code.intValue())setDefault(null);
        FileDs fileDs = dozerMapper.map(fileDsDto,FileDs.class);
        boolean result = super.save(fileDs);
        //把数据源加到spring容器中和工程map中
        if (StrUtil.isNotEmpty(fileDs.getMinioSecretKey())){
            handleSecret(fileDs);
        }
        fileInfoServiceFactory.dynamicAddDs(fileDs);
        return result;
    }

    /**
     * 连接测试
     * @param fileDsDto
     */
    @Override
    public void connect(FileDsDto fileDsDto) {
        FileDs fileDs = dozerMapper.map(fileDsDto, FileDs.class);
        if (StrUtil.isNotEmpty(fileDs.getMinioSecretKey())){
            handleSecret(fileDs);
        }
        fileInfoServiceFactory.dynamicConnect(fileDs);
    }

    /**
    * 修改实体
    * @param fileDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(FileDsDto fileDsDto) {
        /**
         * 检验数据是否已经存在，模仿小孙的写法；
         */
        Optional.ofNullable(getById(fileDsDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的数据源！"));
        Optional.of(count(Wrappers.<FileDs>lambdaQuery().eq(FileDs::getPoolName, fileDsDto.getPoolName()).ne(FileDs::getId,fileDsDto.getId()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("{}已经存在", fileDsDto.getPoolName());
            }
            return true;
        });
        if(ObjectUtil.isNotEmpty(fileDsDto.getIsDefault()) && fileDsDto.getIsDefault().intValue() == YesNo.YES.code.intValue())setDefault(fileDsDto.getId());
        fileDsDto.setUpdateTime(LocalDateTime.now());
        FileDs fileDs = dozerMapper.map(fileDsDto,FileDs.class);
        boolean result = super.updateById(fileDs);
        //把数据源加到spring容器中和工程map中
        fileInfoServiceFactory.removeDs(fileDsDto.getPoolName());
        //把数据源从spring容器中和工程map中删除
        if (StrUtil.isNotEmpty(fileDs.getMinioSecretKey())){
            handleSecret(fileDs);
        }
        fileInfoServiceFactory.dynamicAddDs(fileDs);
        return result;
    }

    /**
     * 处理下密码
     * @param fileDs
     */
    void handleSecret(FileDs fileDs){
        //byte[] aByte = HexUtil.decodeHex(fileDs.getMinioSecretKey());
        byte[] decrypt = rsa.decrypt(fileDs.getMinioSecretKey(), KeyType.PrivateKey);
        fileDs.setMinioSecretKey(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
    }

    /**
     * 设置默认数据源
     * @param id
     * @param isDefault
     * @return
     */
    @Transactional
    @Override
    public Boolean setDefault(Long id,Integer isDefault) {
        LambdaUpdateWrapper<FileDs> updateCurrent = new LambdaUpdateWrapper<>();
        updateCurrent.set(FileDs::getIsDefault,isDefault)
                .eq(FileDs::getId,id);
        update(updateCurrent);
        if(YesNo.YES.code.intValue() == isDefault.intValue()){
            LambdaUpdateWrapper<FileDs> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(FileDs::getIsDefault,YesNo.NO.code.intValue()).
                    eq(FileDs::getIsDefault, YesNo.YES.code.intValue()).
                    ne(FileDs::getId,id);
            update(updateWrapper);
        }
        return true;
    }

    void setDefault(Long id){
        LambdaUpdateWrapper<FileDs> updateWrapper = new LambdaUpdateWrapper<>();
        //如果新增就为默认数据源，那就把其他所有的为yes的设置为No
        if(ObjectUtil.isEmpty(id)){
            updateWrapper.set(FileDs::getIsDefault,YesNo.NO.code.intValue()).eq(FileDs::getIsDefault, YesNo.YES.code.intValue());
        }else {
            //就把除了当前这条，其他为yes的都设置为no
            updateWrapper.set(FileDs::getIsDefault,YesNo.NO.code.intValue()).
                    eq(FileDs::getIsDefault, YesNo.YES.code.intValue()).
                    ne(FileDs::getId,id);
        }
        update(updateWrapper);
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        FileDsVo fileDsVo = get(id);
        boolean result = super.removeById(id);
        //把数据源从spring容器中和工程map中删除
        fileInfoServiceFactory.removeDs(fileDsVo.getPoolName());
        return result;
    }
}
