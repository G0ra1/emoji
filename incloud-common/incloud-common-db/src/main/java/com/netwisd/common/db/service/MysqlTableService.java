package com.netwisd.common.db.service;

import cn.hutool.core.util.StrUtil;
import com.netwisd.common.core.constants.FieldConvert;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Length;
import com.netwisd.common.db.data.ColumnInfo;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.TableInfo;
import com.netwisd.common.db.mapper.MysqlTableMapper;
import com.netwisd.common.db.mysql.ColumnMeta;
import com.netwisd.common.db.util.ClassTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * @Description: 自动建表service层
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:16 下午
 */

@Transactional
@Slf4j
public class MysqlTableService {

    @Autowired
    private MysqlTableMapper mysqlTableMapper;

    /**
     * 要扫描的model所在的package
     */
    @Value("${incloud.table.package}")
    private String pack;

    /**
     * 自动创建模式：update表示更新，create表示删除原表重新创建，none 表示不执行操作
     */
    @Value("${incloud.table.auto}")
    private String tableAuto;

    @Value("${incloud.table.model}")
    private String model;

    /**
     * 读取配置文件的三种状态（创建表、更新表、不做任何事情）
     */
    public void createMysqlTable() {

        // 不做任何事情
        if ("none".equals(tableAuto)) {
            return;
        }

        // 获取Mysql的类型，以及类型需要设置几个长度
        Map<String, Object> mySqlTypeAndLengthMap = mySqlTypeAndLengthMap();



        // 从包package中获取所有的Class
        Set<Class<?>> classes = ClassTools.getClasses(pack);

        // 用于存需要创建的表名+结构
        Map<TableInfo, List<Object>> newTableMap = new HashMap<TableInfo, List<Object>>();

        // 用于存需要更新字段类型等的表名+结构
        Map<TableInfo, List<Object>> modifyTableMap = new HashMap<TableInfo, List<Object>>();

        // 用于存需要增加字段的表名+结构
        Map<TableInfo, List<Object>> addTableMap = new HashMap<TableInfo, List<Object>>();

        // 用于存需要删除字段的表名+结构
        Map<TableInfo, List<Object>> removeTableMap = new HashMap<TableInfo, List<Object>>();

        // 用于存需要删除主键的表名+结构
        Map<TableInfo, List<Object>> dropKeyTableMap = new HashMap<TableInfo, List<Object>>();

        // 用于存需要删除唯一约束的表名+结构
        Map<TableInfo, List<Object>> dropUniqueTableMap = new HashMap<TableInfo, List<Object>>();

        // 构建出全部表的增删改的map
        allTableMapConstruct(mySqlTypeAndLengthMap, classes, newTableMap, modifyTableMap, addTableMap, removeTableMap,
                dropKeyTableMap, dropUniqueTableMap);

        // 根据传入的map，分别去创建或修改表结构
        createOrModifyTableConstruct(newTableMap, modifyTableMap, addTableMap, removeTableMap, dropKeyTableMap,
                dropUniqueTableMap);
    }

    /**
     * 构建出全部表的增删改的map
     *
     * @param mySqlTypeAndLengthMap 获取Mysql的类型，以及类型需要设置几个长度
     * @param classes               从包package中获取所有的Class
     * @param newTableMap           用于存需要创建的表名+结构
     * @param modifyTableMap        用于存需要更新字段类型等的表名+结构
     * @param addTableMap           用于存需要增加字段的表名+结构
     * @param removeTableMap        用于存需要删除字段的表名+结构
     * @param dropKeyTableMap       用于存需要删除主键的表名+结构
     * @param dropUniqueTableMap    用于存需要删除唯一约束的表名+结构
     */
    private void allTableMapConstruct(Map<String, Object> mySqlTypeAndLengthMap, Set<Class<?>> classes,
                                      Map<TableInfo, List<Object>> newTableMap, Map<TableInfo, List<Object>> modifyTableMap,
                                      Map<TableInfo, List<Object>> addTableMap, Map<TableInfo, List<Object>> removeTableMap,
                                      Map<TableInfo, List<Object>> dropKeyTableMap, Map<TableInfo, List<Object>> dropUniqueTableMap) {
        for (Class<?> clas : classes) {

           TableInfo tableInfo = new TableInfo(clas);

            if(!tableInfo.isValid())
                continue;

            // 用于存新增表的字段
            List<Object> newFieldList = new ArrayList<Object>();
            // 用于存删除的字段
            List<Object> removeFieldList = new ArrayList<Object>();
            // 用于存新增的字段
            List<Object> addFieldList = new ArrayList<Object>();
            // 用于存修改的字段
            List<Object> modifyFieldList = new ArrayList<Object>();
            // 用于存删除主键的字段
            List<Object> dropKeyFieldList = new ArrayList<Object>();
            // 用于存删除唯一约束的字段
            List<Object> dropUniqueFieldList = new ArrayList<Object>();

            // 迭代出所有model的所有fields存到newFieldList中
            tableFieldsConstruct(mySqlTypeAndLengthMap, clas, newFieldList);

            // 如果配置文件配置的是create，表示将所有的表删掉重新创建
            if ("create".equals(tableAuto)) {
                mysqlTableMapper.dropTable(tableInfo.getName());
            }

            Boolean exist = mysqlTableMapper.hasTable(tableInfo.getName());

            // 不存在时
            if (!exist) {
                newTableMap.put(tableInfo, newFieldList);
            } else {
                List<ColumnMeta> tableColumnList = mysqlTableMapper.getColumnMetas(tableInfo.getName());

//                List<TableMeta> tableMetas = tableDao.getTableMetas("sys");

                List<String> columnNames = ClassTools.getPropertyValueList(tableColumnList,
                        ColumnMeta.COLUMN_NAME_KEY);

                // 验证对比从model中解析的fieldList与从数据库查出来的columnList
                // 1. 找出增加的字段
                // 2. 找出删除的字段
                // 3. 找出更新的字段
                buildAddAndRemoveAndModifyFields(mySqlTypeAndLengthMap, modifyTableMap, addTableMap, removeTableMap,
                        dropKeyTableMap, dropUniqueTableMap, tableInfo, newFieldList, removeFieldList, addFieldList,
                        modifyFieldList, dropKeyFieldList, dropUniqueFieldList, tableColumnList, columnNames);

            }
        }
    }

    /**
     * 构建增加的删除的修改的字段
     *
     * @param mySqlTypeAndLengthMap 获取Mysql的类型，以及类型需要设置几个长度
     * @param modifyTableMap        用于存需要更新字段类型等的表名+结构
     * @param addTableMap           用于存需要增加字段的表名+结构
     * @param removeTableMap        用于存需要删除字段的表名+结构
     * @param dropKeyTableMap       用于存需要删除主键的表名+结构
     * @param dropUniqueTableMap    用于存需要删除唯一约束的表名+结构
     * @param tableInfo             表
     * @param newFieldList          用于存新增表的字段
     * @param removeFieldList       用于存删除的字段
     * @param addFieldList          用于存新增的字段
     * @param modifyFieldList       用于存修改的字段
     * @param dropKeyFieldList      用于存删除主键的字段
     * @param dropUniqueFieldList   用于存删除唯一约束的字段
     * @param tableColumnList       已存在时理论上做修改的操作，这里查出该表的结构
     * @param columnNames           从sysColumns中取出我们需要比较的列的List
     */
    private void buildAddAndRemoveAndModifyFields(Map<String, Object> mySqlTypeAndLengthMap,
                                                  Map<TableInfo, List<Object>> modifyTableMap, Map<TableInfo, List<Object>> addTableMap,
                                                  Map<TableInfo, List<Object>> removeTableMap, Map<TableInfo, List<Object>> dropKeyTableMap,
                                                  Map<TableInfo, List<Object>> dropUniqueTableMap, TableInfo tableInfo, List<Object> newFieldList,
                                                  List<Object> removeFieldList, List<Object> addFieldList, List<Object> modifyFieldList,
                                                  List<Object> dropKeyFieldList, List<Object> dropUniqueFieldList, List<ColumnMeta> tableColumnList,
                                                  List<String> columnNames) {
        // 1. 找出增加的字段
        // 根据数据库中表的结构和model中表的结构对比找出新增的字段
        buildNewFields(addTableMap, tableInfo, newFieldList, addFieldList, columnNames);

        // 将fieldList转成Map类型，字段名作为主键
        Map<String, ColumnInfo> fieldMap = new HashMap<String, ColumnInfo>();
        for (Object obj : newFieldList) {
            ColumnInfo createTableParam = (ColumnInfo) obj;
            fieldMap.put(createTableParam.getName(), createTableParam);
        }

        if("prod".equals(model)){
            // 2. 找出删除的字段
            buildRemoveFields(removeTableMap, tableInfo, removeFieldList, columnNames, fieldMap);
        }

        // 3. 找出更新的字段
        buildModifyFields(mySqlTypeAndLengthMap, modifyTableMap, dropKeyTableMap, dropUniqueTableMap, tableInfo,
                modifyFieldList, dropKeyFieldList, dropUniqueFieldList, tableColumnList, fieldMap);
    }

    /**
     * 根据数据库中表的结构和model中表的结构对比找出修改类型默认值等属性的字段
     *
     * @param mySqlTypeAndLengthMap 获取Mysql的类型，以及类型需要设置几个长度
     * @param modifyTableMap        用于存需要更新字段类型等的表名+结构
     * @param dropKeyTableMap       用于存需要删除主键的表名+结构
     * @param dropUniqueTableMap    用于存需要删除唯一约束的表名+结构
     * @param table                 表
     * @param modifyFieldList       用于存修改的字段
     * @param dropKeyFieldList      用于存删除主键的字段
     * @param dropUniqueFieldList   用于存删除唯一约束的字段
     * @param tableColumnList       已存在时理论上做修改的操作，这里查出该表的结构
     * @param fieldMap              从sysColumns中取出我们需要比较的列的List
     */
    private void buildModifyFields(Map<String, Object> mySqlTypeAndLengthMap, Map<TableInfo, List<Object>> modifyTableMap,
                                   Map<TableInfo, List<Object>> dropKeyTableMap, Map<TableInfo, List<Object>> dropUniqueTableMap, TableInfo table,
                                   List<Object> modifyFieldList, List<Object> dropKeyFieldList, List<Object> dropUniqueFieldList,
                                   List<ColumnMeta> tableColumnList, Map<String, ColumnInfo> fieldMap) {
        for (ColumnMeta sysColumn : tableColumnList) {
            // 数据库中有该字段时
            ColumnInfo createTableParam = fieldMap.get(sysColumn.getColumnName());
            if (createTableParam != null) {
                // 检查是否要删除已有主键和是否要删除已有唯一约束的代码必须放在其他检查的最前面
                // 原本是主键，现在不是了，那么要去做删除主键的操作
                if ("PRI".equals(sysColumn.getColumnKey()) && !createTableParam.isKey()) {
                    dropKeyFieldList.add(createTableParam);
                }

                // 原本是唯一，现在不是了，那么要去做删除唯一的操作
                if ("UNI".equals(sysColumn.getColumnKey()) && !createTableParam.isUnique()) {
                    dropUniqueFieldList.add(createTableParam);
                }

                // 验证是否有更新
                // 1.验证类型
                if (!sysColumn.getDataType().toLowerCase().equals(createTableParam.getType().toLowerCase())) {
                    modifyFieldList.add(createTableParam);
                    continue;
                }
                // 2.验证长度
                // 3.验证小数点位数
                int length = (Integer) mySqlTypeAndLengthMap.get(createTableParam.getType().toLowerCase());
                String typeAndLength = createTableParam.getType().toLowerCase();
                /**
                 * 1代表只有长度没有精度类型，如：varchar(255)
                 * 2代表有长度和精度的类型 如: double(5,2)
                 */
                if (length == 1) {
                    // 拼接出类型加长度，比如varchar(1)
                    typeAndLength = typeAndLength + "(" + createTableParam.getLength() + ")";
                } else if (length == 2) {
                    // 拼接出类型加长度，比如decimal(5,2)
                    typeAndLength = typeAndLength + "(" + createTableParam.getLength() + ","
                            + createTableParam.getPrecision() + ")";
                }
                /**
                 * 这里解释一下处理的方式，mysql从8.0.18版本之后的（理论上是这样，细节版本没测试，测过8.0.23~8.0.26版本）版本开始，表中的column元数据查询结构发生了改变；
                 * 比如 ：8.0.18版本查询出bigint类型的结果为：column_type为bigint(20)，而23或26版本为：column_type为bigint，而长度字段用另一个字段：character_maximum_length代替 值为：20
                 * 但是，但是...查询varchar类型时，它的column_type依然为varchar(255)，这TMD的就很恶心，要改就统一改，这是玩的哪一套？？？
                 * 附查询语句：select * from information_schema.columns where table_name = 'incloud_xxx' and table_schema = (select database()) order by ordinal_position
                 * 可以对比不同版本之间的区别；
                 * 截止到当前时间——2021/11/17，最新版本为8.0.26版本是这种情况，后续不知道会不会改过来；
                 */
                if(StrUtil.containsAny(sysColumn.getColumnType().toLowerCase(),"(",")")){
                // 判断类型+长度是否相同
                    if (!sysColumn.getColumnType().toLowerCase().equals(typeAndLength)) {
                        modifyFieldList.add(createTableParam);
                        continue;
                    }
                }

                // 4.验证主键
                if (!"PRI".equals(sysColumn.getColumnKey()) && createTableParam.isKey()) {
                    // 原本不是主键，现在变成了主键，那么要去做更新
                    modifyFieldList.add(createTableParam);
                    continue;
                }

                // 5.验证自增
                if ("auto_increment".equals(sysColumn.getExtra()) && !createTableParam.isAutoIncrement()) {
                    modifyFieldList.add(createTableParam);
                    continue;
                }

                // 6.验证默认值
                if (sysColumn.getColumnDefault() == null || sysColumn.getColumnDefault().equals("")) {
                    // 数据库默认值是null，model中注解设置的默认值不为NULL时，那么需要更新该字段
                    if (!"NULL".equals(createTableParam.getDefaultValue())) {
                        modifyFieldList.add(createTableParam);
                        continue;
                    }
                } else if (!sysColumn.getColumnDefault().equals(createTableParam.getDefaultValue())) {
                    // 两者不相等时，需要更新该字段
                    modifyFieldList.add(createTableParam);
                    continue;
                }

                // 7.验证是否可以为null(主键不参与是否为null的更新)
                if (sysColumn.getIsNullable().equals("NO") && !createTableParam.isKey()) {
                    if (createTableParam.isNull()) {
                        // 一个是可以一个是不可用，所以需要更新该字段
                        modifyFieldList.add(createTableParam);
                        continue;
                    }
                } else if (sysColumn.getIsNullable().equals("YES")&& !createTableParam.isKey()) {
                    if (!createTableParam.isNull()) {
                        // 一个是可以一个是不可用，所以需要更新该字段
                        modifyFieldList.add(createTableParam);
                        continue;
                    }
                }

                // 8.验证是否唯一
                if (!"UNI".equals(sysColumn.getColumnKey()) && createTableParam.isUnique()) {
                    // 原本不是唯一，现在变成了唯一，那么要去做更新
                    modifyFieldList.add(createTableParam);
                    continue;
                }

            }
        }

        if (modifyFieldList.size() > 0) {
            modifyTableMap.put(table, modifyFieldList);
        }

        if (dropKeyFieldList.size() > 0) {
            dropKeyTableMap.put(table, dropKeyFieldList);
        }

        if (dropUniqueFieldList.size() > 0) {
            dropUniqueTableMap.put(table, dropUniqueFieldList);
        }
    }

    /**
     * 根据数据库中表的结构和model中表的结构对比找出删除的字段
     *
     * @param removeTableMap  用于存需要删除字段的表名+结构
     * @param table           表
     * @param removeFieldList 用于存删除的字段
     * @param columnNames     数据库中的结构
     * @param fieldMap        model中的字段，字段名为key
     */
    private void buildRemoveFields(Map<TableInfo, List<Object>> removeTableMap, TableInfo table, List<Object> removeFieldList,
                                   List<String> columnNames, Map<String, ColumnInfo> fieldMap) {
        for (String fieldNm : columnNames) {
            // 判断该字段在新的model结构中是否存在
            if (fieldMap.get(fieldNm) == null) {
                // 不存在，做删除处理
                removeFieldList.add(fieldNm);
            }
        }
        if (removeFieldList.size() > 0) {
            removeTableMap.put(table, removeFieldList);
        }
    }

    /**
     * 根据数据库中表的结构和model中表的结构对比找出新增的字段
     *
     * @param addTableMap  用于存需要增加字段的表名+结构
     * @param table        表
     * @param newFieldList model中的结构
     * @param addFieldList 用于存新增的字段
     * @param columnNames  数据库中的结构
     */
    private void buildNewFields(Map<TableInfo, List<Object>> addTableMap, TableInfo table, List<Object> newFieldList,
                                List<Object> addFieldList, List<String> columnNames) {
        for (Object obj : newFieldList) {
            ColumnInfo createTableParam = (ColumnInfo) obj;
            // 循环新的model中的字段，判断是否在数据库中已经存在
            if (!columnNames.contains(createTableParam.getName())) {
                // 不存在，表示要在数据库中增加该字段
                addFieldList.add(obj);
            }
        }
        if (addFieldList.size() > 0) {
            addTableMap.put(table, addFieldList);
        }
    }

    /**
     * 迭代出所有model的所有fields存到newFieldList中
     *
     * @param mySqlTypeAndLengthMap mysql数据类型和对应几个长度的map
     * @param clas                  准备做为创建表依据的class
     * @param newFieldList          用于存新增表的字段
     */
    private void tableFieldsConstruct(Map<String, Object> mySqlTypeAndLengthMap, Class<?> clas,
                                      List<Object> newFieldList) {
        List<Object> tempFieldList = new ArrayList<>();
        Set<String> fieldSet = new HashSet();
        Field[] fields = clas.getDeclaredFields();

        // 判断是否有父类，如果有拉取父类的field，这里只支持多层继承
        fields = recursionParents(clas, fields);

        for (Field field : fields) {
            // 判断方法中是否有指定注解类型的注解
            boolean hasAnnotation = field.isAnnotationPresent(Column.class);
            if (hasAnnotation) {
                // 根据注解类型返回方法的指定类型注解
                Column column = field.getAnnotation(Column.class);
                ColumnInfo columnInfo = new ColumnInfo(field);
                int length = (Integer) mySqlTypeAndLengthMap.get(column.type().toLowerCase());
                columnInfo.setTypeLength(length);
                //对id特殊处理下，把他放到最前面，符合我们平常的使用习惯，根据枚举做自定义ID的字段名映射
                if(fieldSet.contains(columnInfo.getName())){
                    continue;
                }
                if(columnInfo.getName().equals(FieldConvert.ID.getName())){
                    newFieldList.add(columnInfo);
                }else {
                    tempFieldList.add(columnInfo);
                }
                fieldSet.add(columnInfo.getName());
            }
        }
        newFieldList.addAll(tempFieldList);
    }

    /**
     * 递归扫描父类的fields
     *
     * @param clas   类
     * @param fields 属性
     */
    @SuppressWarnings("rawtypes")
    private Field[] recursionParents(Class<?> clas, Field[] fields) {
        if (clas.getSuperclass() != null) {
            Class clsSup = clas.getSuperclass();
            fields = (Field[]) ArrayUtils.addAll(fields, clsSup.getDeclaredFields());
            fields = recursionParents(clsSup, fields);
        }
        return fields;
    }

    /**
     * 根据传入的map创建或修改表结构
     *
     * @param newTableMap        用于存需要创建的表名+结构
     * @param modifyTableMap     用于存需要更新字段类型等的表名+结构
     * @param addTableMap        用于存需要增加字段的表名+结构
     * @param removeTableMap     用于存需要删除字段的表名+结构
     * @param dropKeyTableMap    用于存需要删除主键的表名+结构
     * @param dropUniqueTableMap 用于存需要删除唯一约束的表名+结构
     */
    private void createOrModifyTableConstruct(Map<TableInfo, List<Object>> newTableMap,
                                              Map<TableInfo, List<Object>> modifyTableMap, Map<TableInfo, List<Object>> addTableMap,
                                              Map<TableInfo, List<Object>> removeTableMap, Map<TableInfo, List<Object>> dropKeyTableMap,
                                              Map<TableInfo, List<Object>> dropUniqueTableMap) {
        // 1. 创建表
        createTableByMap(newTableMap);
        // 2. 删除要变更主键的表的原来的字段的主键
        dropFieldsKeyByMap(dropKeyTableMap);
        // 3. 删除要变更唯一约束的表的原来的字段的唯一约束
        dropFieldsUniqueByMap(dropUniqueTableMap);
        // 4. 添加新的字段
        addFieldsByMap(addTableMap);
        // 5. 删除字段
        removeFieldsByMap(removeTableMap);
        // 6. 修改字段类型等
        modifyFieldsByMap(modifyTableMap);

    }

    /**
     * 根据map结构修改表中的字段类型等
     *
     * @param modifyTableMap 用于存需要更新字段类型等的表名+结构
     */
    private void modifyFieldsByMap(Map<TableInfo, List<Object>> modifyTableMap) {
        // 做修改字段操作
        if (modifyTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : modifyTableMap.entrySet()) {
                for (Object obj : entry.getValue()) {
                    Map<TableInfo, Object> map = new HashMap<TableInfo, Object>();
                    map.put(entry.getKey(), obj);
                    ColumnInfo fieldProperties = (ColumnInfo) obj;
                    mysqlTableMapper.modifyColumn(map);
                }
            }
        }
    }

    /**
     * 根据map结构删除表中的字段
     *
     * @param removeTableMap 用于存需要删除字段的表名+结构
     */
    private void removeFieldsByMap(Map<TableInfo, List<Object>> removeTableMap) {
        // 做删除字段操作
        if (removeTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : removeTableMap.entrySet()) {
                for (Object obj : entry.getValue()) {
                    Map<TableInfo, Object> map = new HashMap<TableInfo, Object>();
                    map.put(entry.getKey(), obj);
                    String fieldName = (String) obj;
                    mysqlTableMapper.dropColumn(map);
                }
            }
        }
    }

    /**
     * 根据map结构对表中添加新的字段
     *
     * @param addTableMap 用于存需要增加字段的表名+结构
     */
    private void addFieldsByMap(Map<TableInfo, List<Object>> addTableMap) {
        if (addTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : addTableMap.entrySet()) {
                for (Object obj : entry.getValue()) {
                    Map<TableInfo, Object> map = new HashMap<TableInfo, Object>();
                    map.put(entry.getKey(), obj);
                    ColumnInfo fieldProperties = (ColumnInfo) obj;
                    mysqlTableMapper.addColumns(map);
                }
            }
        }
    }

    /**
     * 根据map结构删除要变更表中字段的主键
     *
     * @param dropKeyTableMap 用于存需要删除主键的表名+结构
     */
    private void dropFieldsKeyByMap(Map<TableInfo, List<Object>> dropKeyTableMap) {
        // 先去做删除主键的操作，这步操作必须在增加和修改字段之前！
        if (dropKeyTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : dropKeyTableMap.entrySet()) {
                for (Object obj : entry.getValue()) {
                    Map<TableInfo, Object> map = new HashMap<TableInfo, Object>();
                    map.put(entry.getKey(), obj);
                    ColumnInfo fieldProperties = (ColumnInfo) obj;
                    mysqlTableMapper.dropPrimaryKey(map);
                }
            }
        }
    }

    /**
     * 根据map结构删除要变更表中字段的唯一约束
     *
     * @param dropUniqueTableMap 用于存需要删除唯一约束的表名+结构
     */
    private void dropFieldsUniqueByMap(Map<TableInfo, List<Object>> dropUniqueTableMap) {
        // 先去做删除唯一约束的操作，这步操作必须在增加和修改字段之前！
        if (dropUniqueTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : dropUniqueTableMap.entrySet()) {
                for (Object obj : entry.getValue()) {
                    Map<TableInfo, Object> map = new HashMap<TableInfo, Object>();
                    map.put(entry.getKey(), obj);
                    mysqlTableMapper.dropIndex(map);
                    mysqlTableMapper.modifyColumn(map);
                }
            }
        }
    }

    /**
     * 根据map结构创建表
     *
     * @param newTableMap 用于存需要创建的表名+结构
     */
    private void createTableByMap(Map<TableInfo, List<Object>> newTableMap) {
        if (newTableMap.size() > 0) {
            for (Entry<TableInfo, List<Object>> entry : newTableMap.entrySet()) {
                Map<TableInfo, List<Object>> map = new HashMap<TableInfo, List<Object>>();
                map.put(entry.getKey(), entry.getValue());
                mysqlTableMapper.createTable(map);
            }
        }
    }

    /**
     * 获取Mysql的类型，以及类型需要设置几个长度，这里构建成map的样式
     * 构建Map(字段名(小写),需要设置几个长度(0表示不需要设置，1表示需要设置一个，2表示需要设置两个))
     */
    public Map<String, Object> mySqlTypeAndLengthMap() {
        Field[] fields = DataType.class.getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : fields) {
            Length length = field.getAnnotation(Length.class);
            map.put(field.getName().toLowerCase(), length.length());
        }
        return map;
    }
}
