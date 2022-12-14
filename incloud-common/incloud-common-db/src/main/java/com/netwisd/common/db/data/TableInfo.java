package com.netwisd.common.db.data;

import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.mysql.TableMeta;
import com.netwisd.common.db.util.HumpConvert;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.List;

/**
 * @Description: 数据库表的定义 此类部分没有使用lombok，因为部分set方法使用其他属性，会导致maven打包时无法识别
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:15 下午
 */
/*@Setter
@Getter*/
public class TableInfo {
    private String name;
    private String prefix;
    private String comment;
    private String engine;
    private String charset;

    //表的主键
    private ColumnInfo pk;
    //表的列名(不包含主键)
    private List<ColumnInfo> columns;
    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    public TableInfo(Class<?> clazz) {
        initFrom(clazz);
    }

    public TableInfo(TableMeta tableMeta) {
        initFrom(tableMeta);
    }

    public void initFrom(TableMeta tableMeta) {
        setName(tableMeta.getTableName());
        setComment(tableMeta.getTableComment());
        setEngine(tableMeta.getEngine());
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    public static TableInfo from(Class<?> clazz) {
        return new TableInfo(clazz);
    }

    public void initFrom(Class<?> clas) {
        Table table = clas.getAnnotation(Table.class);
        if (null == table) {
            return;
        }

        String tableName = table.value();
        if (StringUtils.isEmpty(tableName)) {
            String entityName = clas.getSimpleName();
            // remove for the end of entity
            if (entityName.endsWith("Entity")) {
                entityName = entityName.substring(0, entityName.length() - 6);
            }
            // Camel to underline
            entityName = HumpConvert.HumpToUnderline(entityName);

            if (!entityName.startsWith("_") && !StringUtils.isEmpty(table.prefix()) && !table.prefix().endsWith("_"))
                tableName = "_" + entityName;
            else
                tableName = entityName;
            if (StringUtils.isEmpty(table.prefix()) && tableName.startsWith("_"))
                tableName = tableName.substring(1);
            else
                tableName = table.prefix() + entityName;
        } else
            tableName = table.prefix() + table.value();

        this.name = tableName;
        this.charset = table.charset();
        this.comment = table.comment();
        this.engine = table.engine();
        this.prefix = table.prefix();

    }

    public boolean isValid() {
        return StringUtils.isEmpty(name) ? false : true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setClassName(tableToJava(this.name, prefix));
        setClassname(StringUtils.uncapitalize(className));
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        setName(name);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public ColumnInfo getPk() {
        return pk;
    }

    public void setPk(ColumnInfo pk) {
        this.pk = pk;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
