package com.netwisd.base.model.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.model.dto.ModelEntityDto;
import com.netwisd.base.model.dto.ModelFieldDto;
import com.netwisd.base.model.mapper.TableMapper;
import com.netwisd.base.model.service.TableService;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;
import com.netwisd.common.db.anntation.DsId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @DS("#param")
    @Override
    public Page<ModelEntityVo> queryTablePage(@DsId String dsId, ModelEntityDto modelEntityDto) {
        return tableMapper.queryTablePage(modelEntityDto.getPage(), modelEntityDto.getTableName(), modelEntityDto.getTableNameCh());
    }

    @DS("#param")
    @Override
    public List<ModelFieldVo> queryColumnInfoList(@DsId String dsId, String tableName) {
        return tableMapper.queryColumnInfoList(tableName);
    }

    @DS("#param")
    @Override
    public void dropTable(@DsId String dsId, String tableName) {
        tableMapper.deleteByTableName(tableName);
    }

    @DS("#param")
    @Override
    public ModelEntityVo getTableInfo(@DsId String dsId, String tableName) {
        return tableMapper.getTableInfo(tableName);
    }

    @DS("#param")
    @Override
    public String getExecSql(@DsId String dsId, ModelEntityDto modelEntityDto) {
        new ModelEntityServiceImpl().validationModelEntityDto(modelEntityDto);
        return ObjectUtil.isNull(tableMapper.getTableInfo(modelEntityDto.getOldTableName()))
                ? getCreateTableSql(modelEntityDto)
                : getAlterTableSql(modelEntityDto);
    }

    @DS("#param")
    @Override
    public void execDDLSql(@DsId String dsId, String sql) {
        if (StrUtil.isNotEmpty(sql)) tableMapper.execDDLSql(sql);
    }

    @DS("#param")
    @Override
    public List<Map> execSql(@DsId String dsId, String sql) {
        return tableMapper.execSql(sql);
    }

    private String getAlterTableSql(ModelEntityDto modelEntityDto) {
        List<ModelFieldDto> oldFieldList = modelEntityDto.getOldFieldList();
        List<ModelFieldDto> newFieldList = modelEntityDto.getFiledList();
        List<String> newFieldStrList = newFieldList.stream().map(ModelFieldDto::getName).collect(Collectors.toList());
        List<String> oldFieldStrList = oldFieldList.stream().map(ModelFieldDto::getName).collect(Collectors.toList());
        //1、改表名和注释
        String alterTableStr = getAlterTableStr(modelEntityDto);
        //2、找到创建的字段。新创建的字段 数据库不存在的但是field集合中存在的，为新创建的；
        //List<ModelField> createList = newFieldList.stream().filter(field -> oldFieldStrList.contains(field.getName()) ? false : true).collect(Collectors.toList());
        List<ModelFieldDto> createList = newFieldList.stream().filter(field -> "add".equals(field.getOperateType())).collect(Collectors.toList());
        String addStr = addColumn(createList, modelEntityDto.getTableName());
        //3、找到删除的字段。数据库查询存在，但是field集合中不存在；
        //List<String> removeList = oldFieldStrList.stream().filter(fieldName -> !newFieldStrList.contains(fieldName)).collect(Collectors.toList());
        List<String> removeList = newFieldList.stream().filter(field -> "del".equals(field.getOperateType())).map(ModelFieldDto::getName).collect(Collectors.toList());
        String dropStr = dropColumn(removeList, modelEntityDto.getTableName());
        //4、找到要修改的字段。两个字段名存在；在去数据库update下；
        String alterStr = getAlterTableSql(oldFieldList, newFieldList, modelEntityDto.getTableName());
        return alterTableStr + dropStr + addStr + alterStr;
    }

    private String getAlterTableStr(ModelEntityDto modelEntityDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.equals(modelEntityDto.getOldTableName(), modelEntityDto.getTableName())
                ? ""
                : " ALTER TABLE `" + modelEntityDto.getOldTableName() + "` RENAME TO `" + modelEntityDto.getTableName() + "`; ");
        sb.append(StrUtil.equals(modelEntityDto.getOldTableNameCh(), modelEntityDto.getTableNameCh())
                ? ""
                : " ALTER TABLE `" + modelEntityDto.getTableName() + "` COMMENT '" + modelEntityDto.getTableNameCh() + "'; ");
        return sb.toString();
    }

    private String getAlterTableSql(List<ModelFieldDto> oldFieldList, List<ModelFieldDto> newFieldList, String tableName) {
        //根据页面去找数据库的，能找到，挨个判断；
        StringBuilder sb = new StringBuilder();
        Map<Long, ModelFieldDto> groupMap = newFieldList.stream().collect(Collectors.toMap(ModelFieldDto::getId, Function.identity(), (key1, key2) -> key2));
        for (ModelFieldDto oldField : oldFieldList) {
            ModelFieldDto newField = groupMap.get(oldField.getId());
            if (ObjectUtil.isNotNull(newField) && StrUtil.equals("update", newField.getOperateType())) {
                //1.修改字段类型、长度、精度
                if (!getDbType(newField).equals(getDbType(oldField))) {
                    sb.append("ALTER TABLE `" + tableName + "` MODIFY COLUMN " + newField.getName() + " " + getDbType(newField) + " comment '" + newField.getNameCh() + "'; ");
                }
                //2、修改列注释
                if (!StrUtil.equals(oldField.getNameCh(), newField.getNameCh())) {
                    sb.append("ALTER TABLE `" + tableName + "` MODIFY COLUMN `" + newField.getName() + "` " + getDbType(newField) + " comment '" + newField.getNameCh() + "'; ");
                }
                //3、修改列名称
                if (!StrUtil.equals(oldField.getName(), newField.getName())) {
                    sb.append("ALTER TABLE `" + tableName + "` CHANGE `" + oldField.getName() + "` `" + newField.getName() + "` " + getDbType(newField) + "; ");
                }
                //4、修改默认值(目前设置的全是字符串)
                if (ObjectUtil.isNotNull(newField.getDefaultValue()) && !StrUtil.equals(newField.getDefaultValue(), oldField.getDefaultValue())) {
                    String defaultValue = StrUtil.isEmpty(newField.getDefaultValue()) ? "NULL" : newField.getDefaultValue();
                    sb.append("ALTER TABLE `" + tableName + "` ALTER  COLUMN `" + newField.getName() + "` SET DEFAULT " + defaultValue + "; ");
                }
                //4、判断是主键
                if (newField.getIsKey() != oldField.getIsKey() && newField.getIsKey() == YesNo.NO.getCode()) {
                    //原来是主键，现在不是了删除掉
                    sb.append("ALTER TABLE `" + tableName + "` DROP PRIMARY KEY; ");
                } else if (newField.getIsKey() != oldField.getIsKey() && newField.getIsKey() == YesNo.YES.getCode()) {
                    //原来不是现在是，修改为主键
                    sb.append("ALTER TABLE `" + tableName + "` ADD  PRIMARY KEY(`" + newField.getName() + "`); ");
                }
                //5、修改是否必填
                if (newField.getIsNotNull() != oldField.getIsNotNull() && newField.getIsNotNull() == YesNo.NO.getCode()) {
                    sb.append("ALTER TABLE `" + tableName + "` MODIFY COLUMN `" + newField.getName() + "` " + getDbType(newField) + " NULL; ");
                } else if (newField.getIsNotNull() != oldField.getIsNotNull() && newField.getIsNotNull() == YesNo.YES.getCode()) {
                    sb.append("ALTER TABLE `" + tableName + "` MODIFY COLUMN `" + newField.getName() + "` " + getDbType(newField) + " NOT NULL; ");
                }
            }
        }
        return sb.toString();
    }

    private String getCreateTableSql(ModelEntityDto modelEntityDto) {
        if (CollectionUtil.isEmpty(modelEntityDto.getFiledList())) {
            return StrUtil.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `").append(modelEntityDto.getTableName()).append("`(");
        //删除的数据移除掉
        modelEntityDto.getFiledList().removeIf(x -> "del".equals(x.getOperateType()));
        for (ModelFieldDto field : modelEntityDto.getFiledList()) {
            if (StrUtil.isEmpty(field.getName()) || StrUtil.isEmpty(field.getDbType())) {
                continue;
            }
            appendCommon(field, sb);
            if (field.getIsKey() == YesNo.YES.getCode())
                sb.append(", PRIMARY KEY (`" + field.getName() + "`)");
            if (field.getIsUnique() == YesNo.YES.getCode())
                sb.append(", UNIQUE KEY (`" + field.getName() + "`)");
            if (modelEntityDto.getFiledList().indexOf(field) != modelEntityDto.getFiledList().size() - 1)
                sb.append(",");
        }
        sb.append(") ENGINE=InnoDb DEFAULT CHARSET=utf8");
        if (StrUtil.isNotEmpty(modelEntityDto.getTableNameCh()))
            sb.append(" COMMENT='" + modelEntityDto.getTableNameCh() + "'");
        sb.append(";");
        return sb.toString();
    }

    private void appendCommon(ModelFieldDto field, StringBuilder sb) {
        sb.append(" `").append(field.getName()).append("` ").append(getDbType(field));
        sb.append(field.getIsNotNull() == YesNo.YES.getCode() ? " NOT NULL" : " NULL");
        String defaultValue = StrUtil.isEmpty(field.getDefaultValue()) ? "NULL" : field.getDefaultValue();
        sb.append(StrUtil.isEmpty(field.getDefaultValue()) ? "" : " DEFAULT " + defaultValue + "");
        sb.append(StrUtil.isEmpty(field.getNameCh()) ? "" : " COMMENT '" + field.getNameCh() + "' ");
    }

    public String dropColumn(List<String> removeList, String tableName) {
        StringBuilder sb = new StringBuilder();
        for (String removeField : removeList) {
            //拼接语句前查看当前列是否存在数据库，避免出现编辑时候，新建一列在点击删除
            if (StrUtil.isEmpty(removeField) || ObjectUtil.isNull(tableMapper.getColumnInfo(tableName, removeField))) {
                continue;
            }
            sb.append("ALTER TABLE `" + tableName + "`");
            sb.append(" DROP `" + removeField + "`");
            sb.append(";");
        }
        return sb.toString();
    }

    private String addColumn(List<ModelFieldDto> createList, String tableName) {
        StringBuilder sb = new StringBuilder();
        for (ModelFieldDto field : createList) {
            if (StrUtil.isEmpty(field.getName()) || StrUtil.isEmpty(field.getDbType())) {
                continue;
            }
            sb.append("ALTER TABLE `" + tableName + "` ADD");
            appendCommon(field, sb);
            if (field.getIsKey() == YesNo.YES.getCode())
                sb.append(", PRIMARY KEY (`" + field.getName() + "`)");
            if (field.getIsUnique() == YesNo.YES.getCode())
                sb.append(", UNIQUE KEY (`" + field.getName() + "`)");
            sb.append(";");
        }
        return sb.toString();
    }

    private String getDbType(ModelFieldDto modelField) {
        String precision = (ObjectUtil.isNull(modelField.getPrecision()) || modelField.getPrecision() == 0) ? "" : String.valueOf(modelField.getPrecision());
        String length = StrUtil.isEmpty(precision) ? "(" + modelField.getLength() + ")" : "(" + modelField.getLength() + "," + precision + ")";
        return (ObjectUtil.isNull(modelField.getLength()) || modelField.getLength() == 0)
                ? modelField.getDbType()
                : modelField.getDbType() + length;
    }
}