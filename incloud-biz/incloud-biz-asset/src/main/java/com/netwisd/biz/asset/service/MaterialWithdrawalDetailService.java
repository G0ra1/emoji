package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialWithdrawalDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDetailDto;
import com.netwisd.biz.asset.vo.MaterialWithdrawalDetailVo;
/**
 * @Description 资产退库详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
public interface MaterialWithdrawalDetailService extends BatchService<MaterialWithdrawalDetail> {
    Page list(MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
    Page lists(MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
    MaterialWithdrawalDetailVo get(Long id);
    void save(MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
    void saveList(List<MaterialWithdrawalDetailDto> materialWithdrawalDetailDtos);
    void update(MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
    void updateSub(MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialWithdrawalDetailVo> getByFkIdVo(Long id);
}
