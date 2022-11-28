package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.ScrapRegisterDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ScrapRegisterDetailDto;
import com.netwisd.biz.asset.vo.ScrapRegisterDetailVo;
/**
 * @Description 报废登记详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
public interface ScrapRegisterDetailService extends BatchService<ScrapRegisterDetail> {
    Page list(ScrapRegisterDetailDto scrapRegisterDetailDto);
    Page lists(ScrapRegisterDetailDto scrapRegisterDetailDto);
    ScrapRegisterDetailVo get(Long id);
    void save(ScrapRegisterDetailDto scrapRegisterDetailDto);
    void saveList(List<ScrapRegisterDetailDto> scrapRegisterDetailDtos);
    void update(ScrapRegisterDetailDto scrapRegisterDetailDto);
    void updateSub(ScrapRegisterDetailDto scrapRegisterDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<ScrapRegisterDetailVo> getByFkIdVo(Long id);
}
