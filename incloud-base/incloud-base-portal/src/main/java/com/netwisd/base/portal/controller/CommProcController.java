package com.netwisd.base.portal.controller;

import com.netwisd.base.portal.constants.BizTagEnum;
import com.netwisd.base.portal.service.PortalContentBannersService;
import com.netwisd.base.portal.service.*;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 流程事件统计处理  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-16 11:38:29
 */
@RestController
@AllArgsConstructor
@Api(value = "commproc", tags = "流程事件统计处理 Controller")
@Slf4j
public class CommProcController {
    //会议费事项申请
    //private final ExpenseMatterMeetingService expenseMatterMeetingService;

    //新闻类内容申请
    private final PortalContentNewsService portalContentNewsService;
    //调整 新闻类内容申请
    private final PortalContentAdjNewsService portalContentAdjNewsService;
    //banner类内容
    private final PortalContentBannersService portalContentBannersService;
    //调整表banner类内容
    private final  PortalContentAdjBannersService portalContentAdjBannersService;
    //文件下载类型内容申请
    private final PortalContentFilesService portalContentFilesService;
    //调整 文件下载类型内容
    private final PortalContentAdjFilesService portalContentAdjFilesService;
    //图片新闻类内容申请
    private final PortalContentPicnewsService portalContentPicnewsService;
    //调整图片新闻类内容申请
    private final PortalContentAdjPicnewsService portalContentAdjPicnewsService;
    //图片轮播类内容申请
    private final PortalContentPicturesService portalContentPicturesService;
    //系统集成类内容申请
    private final PortalContentSysjointsService portalContentSysjointsService;
    //视频类内容
    private final PortalContentVideosService portalContentVideosService;
    //调整表视频类内容
    private final  PortalContentAdjVideosService portalContentAdjVideosService;
    /**
     * 解析执行Task表达式
     * @param executionEntity 解析执行Task表达式
     * @return
     */
    @ApiOperation(value = "解析执行Task表达式", notes = "解析执行Task表达式")
    @PostMapping("/expressionTaskParser")
    public Result expressionTaskParser(@RequestBody ExecutionEntity executionEntity) {
        if(null != executionEntity) {
            if(StringUtils.isNotBlank(executionEntity.getBizTag())) {
                //会议费事项相关
//                if(BizTagEnum.会议费事项提交前.getCode().equals(executionEntity.getBizTag())) { //提交前
//                    expenseMatterMeetingService.procMatterMeetingBeforeSubmit(executionEntity.getProcessInstanceId());
//                } else if(BizTagEnum.会议费事项提交后.getCode().equals(executionEntity.getBizTag())) { //提交后
//                    expenseMatterMeetingService.procMatterMeetingAfterSubmit(executionEntity.getProcessInstanceId());
//                } else if(BizTagEnum.会议费事项撤回.getCode().equals(executionEntity.getBizTag())) { //流程撤回
//                    expenseMatterMeetingService.procDraftRevoke(executionEntity.getProcessInstanceId());
//                } else if(BizTagEnum.会议费事项被驳回.getCode().equals(executionEntity.getBizTag())) { //被驳回
//                    expenseMatterMeetingService.backToDraft(executionEntity.getProcessInstanceId());
//                }

                //新闻类内容相关

            } else {
                throw new IncloudException("bizTag，不能为空。");
            }
        } else {
            throw new IncloudException("任务事件-流程事件统计处理，请传递正确的流程相关数据。");
        }
        return Result.success();
    }

    /**
     * 解析执行Execution表达式
     * @param executionEntity 解析执行Execution表达式
     * @return
     */
    @ApiOperation(value = "解析执行Execution表达式", notes = "解析执行Execution表达式")
    @PostMapping("/expressionExecutionParser")
    public Result expressionExecutionParser(@RequestBody ExecutionEntity executionEntity) {
        if(null != executionEntity) {
            if(StringUtils.isNotBlank(executionEntity.getBizTag())) {
                //全面预算填报
//                if(BizTagEnum.全面预算审批完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    budgetInstfillApplyService.budgetFillAuditSucceed(executionEntity.getProcessInstanceId());
//                } else if(BizTagEnum.全面预算程终止.getCode().equals(executionEntity.getBizTag())) {//流程终止
//                    budgetInstfillApplyService.budgetFillTermination(executionEntity.getProcessInstanceId());
//                }else if(BizTagEnum.全面预算流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    budgetInstfillApplyService.budgetFillDel(executionEntity.getProcessInstanceId());
//                }



                //新闻类内容
//                if (BizTagEnum.新闻类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    portalContentNewsService.auditSucceed(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.新闻类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
//                    portalContentNewsService.procStop(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.新闻类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    portalContentNewsService.procDel(executionEntity.getProcessInstanceId());
//                }

                //调整 新闻类内容
                if (BizTagEnum.调整新闻类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentAdjNewsService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整新闻类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentAdjNewsService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整新闻类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentAdjNewsService.procDel(executionEntity.getProcessInstanceId());
                }

                //banner类内容//todo 取消流程先注释掉
               /* if (BizTagEnum.banner类内容类完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentBannersService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.banner类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentBannersService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.banner类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentBannersService.procDel(executionEntity.getProcessInstanceId());
                }*/

                //调整表banner类内容
                if (BizTagEnum.调整表banner类内容类完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentAdjBannersService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整表banner类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentAdjBannersService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整表banner类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentAdjBannersService.procDel(executionEntity.getProcessInstanceId());
                }

                //文件下载类内容
//                if (BizTagEnum.文件下载类型内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    portalContentFilesService.auditSucceed(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.文件下载类型内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
//                    portalContentFilesService.procStop(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.文件下载类型内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    portalContentFilesService.procDel(executionEntity.getProcessInstanceId());
//                }

                //调整文件下载类内容
                if (BizTagEnum.调整文件下载类型内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentAdjFilesService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整文件下载类型内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentAdjFilesService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整文件下载类型内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentAdjFilesService.procDel(executionEntity.getProcessInstanceId());
                }

//                //图片新闻类内容
//                if (BizTagEnum.图片新闻类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    portalContentPicnewsService.auditSucceed(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.图片新闻类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
//                    portalContentPicnewsService.procStop(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.图片新闻类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    portalContentPicnewsService.procDel(executionEntity.getProcessInstanceId());
//                }

                //调整图片新闻类内容
                if (BizTagEnum.调整图片新闻类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentAdjPicnewsService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整图片新闻类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentAdjPicnewsService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整图片新闻类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentAdjPicnewsService.procDel(executionEntity.getProcessInstanceId());
                }

                //图片轮播类内容
//                if (BizTagEnum.图片轮播类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    portalContentPicturesService.auditSucceed(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.图片轮播类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
//                    portalContentPicturesService.procStop(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.图片轮播类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    portalContentPicturesService.procDel(executionEntity.getProcessInstanceId());
//                }

                //系统集成类内容
//                if (BizTagEnum.系统集成类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
//                    portalContentSysjointsService.auditSucceed(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.系统集成类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
//                    portalContentSysjointsService.procStop(executionEntity.getProcessInstanceId());
//                }
//                if (BizTagEnum.系统集成类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
//                    portalContentSysjointsService.procDel(executionEntity.getProcessInstanceId());
//                }

                /*//视频类内容
                if (BizTagEnum.视频类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentVideosService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.视频类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentVideosService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.视频类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentVideosService.procDel(executionEntity.getProcessInstanceId());
                }*/

                //调整表视频类内容
                if (BizTagEnum.调整表视频类内容完成.getCode().equals(executionEntity.getBizTag())) { //审批完成
                    portalContentAdjVideosService.auditSucceed(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整表视频类内容流程终止.getCode().equals(executionEntity.getBizTag())) { //流程终止
                    portalContentAdjVideosService.procStop(executionEntity.getProcessInstanceId());
                }
                if (BizTagEnum.调整表视频类内容流程删除.getCode().equals(executionEntity.getBizTag())) { //流程删除
                    portalContentAdjVideosService.procDel(executionEntity.getProcessInstanceId());
                }

            } else {
                throw new IncloudException("bizTag，不能为空。");
            }
        } else {
            throw new IncloudException("执行事件-流程事件统计处理，请传递正确的流程相关数据。");
        }
        return Result.success();
    }

}
