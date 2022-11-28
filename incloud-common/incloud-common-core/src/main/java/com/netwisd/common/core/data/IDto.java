package com.netwisd.common.core.data;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

/**
 * @Description: 目的是为了扩展一些公共属性；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/19 10:03 下午
 */
@ApiModel(value = "公共Dto")
@Slf4j
@Data
@AllArgsConstructor
public class IDto implements IValidation {

    /**
     * 主机和进程的机器码
     */
    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    /**
     * id
     * 主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

    /**
     * id自动赋值标识
     */
    @ApiModelProperty( value="请忽略我，不要传值！" )
    public Boolean idSign;

    public Boolean getIdSign() {
        return idSign;
    }

    public void setIdSign(Boolean idSign) {
        this.idSign = idSign;
    }

    /**
     * 创建日期
     */
    @ApiModelProperty( value="create_time" )
    public LocalDateTime createTime;

    /**
     * 修改日期
     */
    @ApiModelProperty( value="update_time" )
    public LocalDateTime updateTime;

    /**
     * page对象，用于页面传参，给个默认值
     * 一般传current（默认1），size（默认10）
     */
    @ApiModelProperty( value="page" )
    public Page page;

    //
    public IDto(Args args) {
        if(args.type.equals(Args.NULL_ID.type)){
            init();
        }else if(args.type.equals(Args.PAGE.type)){
            setPage(new Page());
        }else if(args.type.equals(Args.NULL.type)){
            //Nothing
        }else {
            init();
            setId(id);
        }
    }

    public IDto() {
        init();
        setId(id);
    }

    private void init(){
        LocalDateTime now = LocalDateTime.now();
        setCreateTime(now);
        setUpdateTime(now);
        setPage(new Page());
    }


    /*public IDto(Long id, LocalDateTime createTime, LocalDateTime updateTime, Page page) {
        setId(id);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        setPage(page);
    }*/

    public Long getId() {
        return ObjectUtil.isNotEmpty(id) && id.longValue() ==0 ? null : id;
    }

    public void setId(Long id) {
        if(ObjectUtil.isNotEmpty(id)){
            /*log.info("id notempty：{}",id);*/
            if(id.longValue() ==0){
                this.id = null;
            }else {
                this.id = id;
                this.idSign = false;
            }
        }
        if(ObjectUtil.isEmpty(id)){
            /*log.info("this.id前 {} id前：{}",this.id,id);*/
            this.id = IDENTIFIER_GENERATOR.nextId(this).longValue();
            /*log.info("this.id后 {} id后：{}",this.id,id);*/
            this.idSign = true;
        }
    }
}
