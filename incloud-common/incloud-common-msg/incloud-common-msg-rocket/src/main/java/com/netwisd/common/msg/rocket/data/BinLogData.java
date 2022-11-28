package com.netwisd.common.msg.rocket.data;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class BinLogData<T> implements Serializable {

    private static final long serialVersionUID = 4299844634380400999L;

    //INSERT、UPDATE、DELETE
    private String type;

    private Long ts;

    private String table;

    private Map<String, Integer> sqlType;

    private String sql;

    private List<String> pkNames;

    private List<T> old;

    private Map<String, String> mysqlType;

    private boolean isDdl;

    private Long id;

    private Long es;

    private String database;

    private List<T> data;
}
