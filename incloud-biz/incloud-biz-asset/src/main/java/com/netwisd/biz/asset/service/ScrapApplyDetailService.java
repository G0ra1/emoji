package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.ScrapApplyDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ScrapApplyDetailDto;
import com.netwisd.biz.asset.vo.ScrapApplyDetailVo;
/**
 * @Description 报废申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
public interface ScrapApplyDetailService extends BatchService<ScrapApplyDetail> {
    Page list(ScrapApplyDetailDto scrapApplyDetailDto);
    Page lists(ScrapApplyDetailDto scrapApplyDetailDto);
    ScrapApplyDetailVo get(Long id);
    void save(ScrapApplyDetailDto scrapApplyDetailDto);
    void saveList(List<ScrapApplyDetailDto> scrapApplyDetailDtos);
    void update(ScrapApplyDetailDto scrapApplyDetailDto);
    void updateSub(ScrapApplyDetailDto scrapApplyDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<ScrapApplyDetailVo> getByFkIdVo(Long id);

    List<ScrapApplyDetailVo> getByApplyIds(String applyIds);
}
