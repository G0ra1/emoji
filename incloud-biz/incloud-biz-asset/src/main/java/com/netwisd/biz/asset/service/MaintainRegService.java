package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainReg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainRegDto;
import com.netwisd.biz.asset.vo.MaintainRegVo;
/**
 * @Description 维修登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
public interface MaintainRegService extends BatchService<MaintainReg> {
    Page list(MaintainRegDto maintainRegDto);
    Page lists(MaintainRegDto maintainRegDto);
    MaintainRegVo get(Long id);
    void save(MaintainRegDto maintainRegDto);
    void saveList(List<MaintainRegDto> maintainRegDtos);
    void update(MaintainRegDto maintainRegDto);
    void updateSub(MaintainRegDto maintainRegDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainRegVo> getByFkIdVo(Long id);

    /**
     * 通过流程实例id查询维修登记详情
     * @param procInstId procInstId
     * @return Result
     */
    MaintainRegVo getByProc(String procInstId);
}
