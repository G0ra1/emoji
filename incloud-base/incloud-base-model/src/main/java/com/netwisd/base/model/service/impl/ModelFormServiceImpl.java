package com.netwisd.base.model.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.model.vo.ModelFormVo;
import com.netwisd.base.model.dto.ModelFormDto;
import com.netwisd.base.model.entity.ModelForm;
import com.netwisd.base.model.mapper.ModelFormMapper;
import com.netwisd.base.model.service.ModelFormButtonService;
import com.netwisd.base.model.service.ModelFormService;
import com.netwisd.base.model.service.ModelingFieldService;
import com.netwisd.base.model.vo.FormContentVo;
import com.netwisd.base.model.vo.FormFileDirVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@AllArgsConstructor
public class ModelFormServiceImpl extends ServiceImpl<ModelFormMapper, ModelForm> implements ModelFormService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ModelFormButtonService modelFormButtonService;

    @Autowired
    private ModelingFieldService modelingFieldService;

    @Override
    public Page queryModelFormPage(ModelFormDto modelFormDto) {
        return page(modelFormDto.getPage(), Wrappers.<ModelForm>lambdaQuery()
                .eq(ObjectUtil.isNotNull(modelFormDto.getModelTypeId()), ModelForm::getModelTypeId, modelFormDto.getModelTypeId())
                .eq(ObjectUtil.isNotNull(modelFormDto.getFormType()), ModelForm::getFormType, modelFormDto.getFormType())
                .like(ObjectUtil.isNotNull(modelFormDto.getModelTypeName()), ModelForm::getModelTypeName, modelFormDto.getModelTypeName())
                .like(StrUtil.isNotEmpty(modelFormDto.getFormName()), ModelForm::getFormName, modelFormDto.getFormName())
                .like(StrUtil.isNotEmpty(modelFormDto.getFormNameCh()), ModelForm::getFormNameCh, modelFormDto.getFormNameCh())
                .orderByDesc(ModelForm::getCreateTime));
    }

    @Override
    @Transactional
    public boolean saveModelForm(ModelFormDto modelFormDto) {
        Optional.ofNullable(count(Wrappers.<ModelForm>lambdaQuery().eq(ModelForm::getFormName, modelFormDto.getFormName()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("表单Code已经存在");
            }
            return true;
        });
        //保存前，删除，在重新保存
        modelFormButtonService.delModelFormButton(modelFormDto.getId());
        modelFormButtonService.saveModelFormButton(modelFormDto, modelFormDto.getButtonList());
        return save(dozerMapper.map(modelFormDto, ModelForm.class));
    }

    @Override
    @Transactional
    public boolean upModelForm(ModelFormDto modelFormDto) {
        Optional.ofNullable(getById(modelFormDto.getId())).orElseThrow(() -> new IncloudException("未获取到表单数据"));
        Optional.ofNullable(count(Wrappers.<ModelForm>lambdaQuery().ne(ModelForm::getId, modelFormDto.getId()).eq(ModelForm::getFormName, modelFormDto.getFormName())))
                .filter(x -> {
                    if (x > 0) {
                        throw new IncloudException("表单Code已经存在");
                    }
                    return true;
                });
        //保存前，删除，在重新保存
        modelFormButtonService.delModelFormButton(modelFormDto.getId());
        modelFormButtonService.saveModelFormButton(modelFormDto, modelFormDto.getButtonList());
        return updateById(dozerMapper.map(modelFormDto, ModelForm.class));
    }

    @Override
    @Transactional
    public boolean delModelForm(Long id) {
        modelFormButtonService.delModelFormButton(id);
        return removeById(id);
    }

    @Override
    @Transactional
    public boolean bindButton(ModelFormDto modelFormDto) {
        modelFormButtonService.delModelFormButton(modelFormDto.getId());
        modelFormButtonService.saveModelFormButton(modelFormDto, modelFormDto.getButtonList());
        return updateById(dozerMapper.map(modelFormDto, ModelForm.class));
    }

    @Override
    public ModelFormVo getModelFormDetail(Long id) {
        ModelFormVo modelFormVo = Optional.ofNullable(dozerMapper.map(getById(id), ModelFormVo.class)).orElseThrow(() -> new IncloudException("未获取到表单数据"));
        //表单按钮
        modelFormVo.setButtonList(modelFormButtonService.queryModelFormButtonList(id));
        //表单字段
        modelFormVo.setFieldList(modelingFieldService.queryModelingFieldList(modelFormVo.getModelingId()));
        return modelFormVo;
    }

    @Override
    public ModelFormVo getModelFormDetailByEasy(String id) {
        ModelFormVo modelFormVo = Optional.ofNullable(
                dozerMapper.map(baseMapper.selectOne(buildModelFormLambdaQuery().eq(ModelForm::getId, id)), ModelFormVo.class)
        ).orElseThrow(() -> new IncloudException("未获取到表单数据"));
        return modelFormVo;
    }

    @Override
    public ModelFormVo getModelFormDetailByFormName(String formName) {
        ModelFormVo modelFormVo = Optional.ofNullable(
                dozerMapper.map(baseMapper.selectOne(buildModelFormLambdaQuery().eq(ModelForm::getFormName, formName).last(" limit 1")), ModelFormVo.class)
        ).orElseThrow(() -> new IncloudException("未获取到表单数据"));
        return modelFormVo;
    }

    @Override
    public byte[] download(Long id) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        ModelForm modelForm = getById(id);
        this.buildZip(modelForm.getFormContent(), zipOutputStream);
        IoUtil.close(zipOutputStream);
        return outputStream.toByteArray();
    }

    private LambdaQueryWrapper<ModelForm> buildModelFormLambdaQuery() {
        return Wrappers.<ModelForm>lambdaQuery().select(
                ModelForm::getId, ModelForm::getFormName, ModelForm::getFormNameCh,
                ModelForm::getPageUrl, ModelForm::getPageUrl,
                ModelForm::getFormSaveUrl, ModelForm::getFormSaveMethodType,
                ModelForm::getFormDeleteUrl, ModelForm::getFormDeleteMethodType,
                ModelForm::getFormUpdateUrl, ModelForm::getFormUpdateMethodType,
                ModelForm::getFormGetUrl, ModelForm::getFormGetMethodType
        );
    }

    private void buildZip(String formContentStr, ZipOutputStream zipOutputStream) throws IOException {
        FormContentVo formContent = JSONUtil.toBean(formContentStr, FormContentVo.class);
        for (FormFileDirVo file : formContent.getFileDir()) {
            if ("dir".equals(file.getType())) {
                addDir(file.getName(), zipOutputStream);
            } else if ("file".equals(file.getType()) && StrUtil.isNotEmpty(file.getContents())) {
                addFile(IOUtils.toInputStream(Base64.decodeStr(file.getContents()), Charset.defaultCharset()), file.getName(), zipOutputStream);
            }
        }
    }

    private void addDir(String path, ZipOutputStream out) throws IOException {
        if (StrUtil.isEmpty(path)) {
            return;
        }
        path = StrUtil.addSuffixIfNot(path, StrUtil.SLASH);
        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            log.error("zip打包文件夹失败:{}", e);
            throw new IncloudException("打包失败");
        } finally {
            out.closeEntry();
        }
    }

    private void addFile(InputStream in, String path, ZipOutputStream out) throws IOException {
        if (null == in) {
            return;
        }
        try {
            out.putNextEntry(new ZipEntry(path));
            IoUtil.copy(in, out);
        } catch (Exception e) {
            log.error("zip打包文件失败:{}", e);
            throw new IncloudException("打包失败");
        } finally {
            out.closeEntry();
            IoUtil.close(in);
        }
    }
}
