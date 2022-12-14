package com.netwisd.common.db.data;

import cn.hutool.core.util.StrUtil;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.mysql.ColumnMeta;
import com.netwisd.common.db.util.HumpConvert;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.lang.reflect.Field;

/**
 * @Description: 列信息的定义，此类部分没有使用lombok，因为部分set方法使用其他属性，会导致maven打包时无法识别
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:16 下午
 */
/*@Data*/
public class ColumnInfo {
    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 类型长度
     */
    private long length;

    /**
     * 类型小数长度
     */
    private int precision;

    /**
     * 字段是否非空
     */
    private boolean isNull;

    /**
     * 字段是否是主键
     */
    private boolean isKey;

    /**
     * 主键是否自增
     */
    private boolean isAutoIncrement;

    /**
     * 字段默认值
     */
    private String defaultValue;

    /**
     * 该类型需要几个长度（例如，需要小数位数的，那么总长度和小数长度就是2个长度）一版只有0、1、2三个可选值，自动从配置的类型中获取的
     */
    private int typeLength;

    /**
     * 值是否唯一
     */
    private boolean isUnique;

    /**
     * 字段注释
     */
    private String comment;

    //属性名称(第一个字母大写)，如：user_name => UserName
    private String attrName;
    //属性名称(第一个字母小写)，如：user_name => userName
    private String attrname;
    //属性类型 列的数据类型，转换成Java类型
    private String attrType;
    //auto_increment
    private String extra;

    private String genAnnotation;


    public ColumnInfo(Field field) {
        initFrom(field);
    }

    public ColumnInfo(ColumnMeta field) {
        initFrom(field);
    }

    public static ColumnInfo from(Field field) {
        return new ColumnInfo(field);
    }

    public void initFrom(ColumnMeta column) {
        setName(column.getColumnName());
        setType(column.getDataType());
        setComment(column.getColumnComment());
        setExtra(column.getExtra());
        if ("PRI".equalsIgnoreCase(column.getColumnKey())) {
            setKey(true);
        }
        setNull(column.getIsNullable().equals("YES")?true:false);
        String ct = column.getColumnType();
        String[] tt = ct.split("\\(");
        if (tt.length > 1) {
            String len = tt[1].split("\\)")[0];
            //int length = Integer.valueOf(len);
            //改成long
            long length = Long.valueOf(len);
            setLength(length);
        } else
            length = 0;
        setGenAnnotation(buildAnnotation());
    }

    private String buildAnnotation() {
        StringBuilder sb = new StringBuilder();
        sb.append("@Column(");
        String divider = "";
        if (this.isKey) {
            sb.append("isKey = true");
            divider = ", ";
        }
        if (!"varchar".equalsIgnoreCase(type)) {
            sb.append(divider + "type = \"" + type + "\"");
            divider = ", ";
        }
        if (length != 255 && length != 0) {
            sb.append(divider + "length = " + length);
            divider = ", ";
        }
        if (!isNull) {
            sb.append(divider + "isNull = false");
            divider = ", ";
        }
        if (isAutoIncrement) {
            sb.append(divider + "isAutoIncrement = true");
            divider = ", ";
        }
        if (!StrUtil.isEmpty(comment)) {
            sb.append(divider + "comment = \"" + comment + "\"");
        }
        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        String dd = "bigint(20)";
        String[] tt = dd.split("\\(");
        String type = tt[0];
        String len = tt[1].split("\\)")[0];
        System.out.print(tt[0]);
    }

    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    public void initFrom(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (null == column) {
            return;
        }
        String columnName = column.value();
        if (StrUtil.isEmpty(columnName)) {
            columnName = field.getName();
            columnName = HumpConvert.HumpToUnderline(columnName);
            if (columnName.startsWith("_"))
                columnName = columnName.substring(1);
        }
        this.name = columnName;
        this.type = column.type().toLowerCase();
        this.length = column.length();
        this.precision = column.precision();
        // 主键或唯一键时设置必须不为null
        if (column.isKey() || column.isUnique())
            this.isNull = false;
        else
            this.isNull = column.isNull();
        this.isKey = column.isKey();
        this.isAutoIncrement = column.isAutoIncrement();
        this.defaultValue = column.defaultValue();
        this.isUnique = column.isUnique();
        this.comment = column.comment();
    }

    public boolean isValid() {
        return StrUtil.isEmpty(this.name) ? false : true;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getTypeLength() {
        return typeLength;
    }

    public void setTypeLength(int typeLength) {
        this.typeLength = typeLength;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getGenAnnotation() {
        return genAnnotation;
    }

    public void setGenAnnotation(String genAnnotation) {
        this.genAnnotation = genAnnotation;
    }

    public void setName(String name) {
        this.name = name;
        setAttrName(columnToJava(name));
        setAttrname(StringUtils.uncapitalize(attrName));
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
        if ("auto_increment".equalsIgnoreCase(this.extra)) {
            this.isAutoIncrement = true;
        }
    }
}
