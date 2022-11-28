package com.netwisd.base.portal.controller;

import com.netwisd.base.portal.dto.PortalContentNewsDto;
import com.netwisd.base.portal.service.PortalContentNewsService;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 同步oa新闻、通告
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oaNews" )
@Api(value = "portalContentNews", tags = "新闻通告类内容发布Controller")
@Slf4j
public class PortalContentOaNewsController {
    @Autowired
    private PortalContentNewsService newsService;

    /**
     * 新增oa新闻/通告
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增oa新闻/通告", notes = "新增oa新闻/通告")
    @PostMapping(value = "/saveNews")
    public Result<Boolean> saveNews(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        Boolean result = newsService.saveNews(portalContentNewsDto);
        log.debug("新增oa新闻/通告成功，oaId:"+portalContentNewsDto.getOaId());
        return Result.success(result);
    }

    /**
     * 修改oa新闻/通告
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改oa新闻/通告", notes = "修改oa新闻/通告")
    @PostMapping(value = "/updateNews")
    public Result<Boolean> updateNews(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        Boolean result = newsService.updateNews(portalContentNewsDto);
        log.debug("修改oa新闻/通告成功，oaId:"+portalContentNewsDto.getOaId());
        return Result.success(result);
    }

    /**
     * oa新闻/通告删除
     * @param oaId
     * @return
     */
    @ApiOperation(value = "oa新闻/通告删除", notes = "oa新闻/通告删除")
    @GetMapping(value = "/deleteNews")
    public Result<Boolean> deleteNews(@RequestParam(value = "oaId",required = false) String oaId,
                                      @RequestParam(value = "partCode",required = false) String partCode) {
        Boolean result = newsService.deleteNews(oaId, partCode);
        log.debug("oa新闻/通告删除成功，oaId:"+oaId);
        return Result.success(result);
    }

    /**
     * 修改创建人信息
     * @return
     */
    @ApiOperation(value = "修改创建人信息", notes = "修改创建人信息")
    @GetMapping(value = "/updateCreateUserMsg")
    public Result updateCreateUserMsg() {
        newsService.updateCreateUserMsg();
        return Result.success();
    }
}
