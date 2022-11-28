package com.netwisd.common.es.index;

import com.netwisd.common.core.data.IValidation;
import lombok.Data;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/16 12:23 下午
 */
@Data
public class IndexDto implements IValidation {
    private List<String> index;
    private Boolean isRebuild = true;
    public Boolean getIsRebuild(){
        return isRebuild;
    }
}
