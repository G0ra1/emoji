package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.mdm.entity.MdmUser;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 用户 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
@Mapper
public interface MdmUserMapper extends BaseMapper<MdmUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmUserDto
     * @return
     */
    Page<MdmUserVo> getPageList(Page page, @Param("mdmUserDto") MdmUserDto mdmUserDto);

    /**
     * 处理列表兼岗 以及排序
     * @param page
     * @param mdmUserDto
     * @return
     */
    Page<MdmUserVo> getConditionList(Page page, @Param("mdmUserDto") MdmUserDto mdmUserDto);

    /**
     * 同步宏景主数据 查询身份证信息，由于宏景身份证号存在x和X所以单独做一下查询 如果身份证号确实重复 取后修改时间
     * @param idCard
     * @return
     */
    MdmUserVo getByIdCard(@Param("idCard") String idCard);
}
