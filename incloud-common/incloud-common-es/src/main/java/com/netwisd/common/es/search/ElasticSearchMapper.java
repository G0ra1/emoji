package com.netwisd.common.es.search;

import com.netwisd.common.core.data.IValidation;
import com.netwisd.common.es.config.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: es搜索用的父类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/11 3:56 下午
 */
@Data
@Slf4j
public abstract class ElasticSearchMapper implements IValidation {

    /**
     * 索引名称，可不传;
     * 如果不传，默认业务中传入当前模块的索引名；
     * 当然也可以传通配符的方式；
     * 例如：incloud4_incloud_base_*
     */
    @ApiModelProperty(value="索引名称，可不传")
    protected String index;

    @ApiModelProperty(value="搜索关键字")
    protected String keyWords;

    public abstract HighlightBuilder highlightBuilder();

    public abstract FunctionScoreQueryBuilder functionScoreQueryBuilder();

    @ApiModelProperty(value="分页，如果是业务上，这个值不用传")
    protected Page page = new Page();

    public String localDateTimeFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String result = localDateTime.format(formatter);
        log.info("日期格式化为:"+result);
        return result;
    }
}
