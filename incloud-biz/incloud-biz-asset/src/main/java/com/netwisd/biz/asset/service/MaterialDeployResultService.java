package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.MaterialDeployResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.biz.asset.vo.MaterialDeployResultVo;
import java.util.List;

/**
 * @Description 资产调配结果数据 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 11:05:25
 */
public interface MaterialDeployResultService extends IService<MaterialDeployResult> {
    Page<MaterialDeployResultVo> page(MaterialDeployResultDto materialDeployResultDto);
    List<MaterialDeployResultVo> list(MaterialDeployResultDto materialDeployResultDto);
    MaterialDeployResultVo get(Long id);
    Boolean save(MaterialDeployResultDto materialDeployResultDto);
    Boolean update(MaterialDeployResultDto materialDeployResultDto);
    Boolean delete(Long id);

    Page<MaterialDeployResultVo> getResultByDeploy(MaterialDeployResultDto materialDeployResultDto);
}
