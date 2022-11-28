package com.netwisd.base.wf.cache;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.feign.MdmFeignClient;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 流程启动时加载缓存
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/15 5:21 下午
 */

@Slf4j
@Component
@Data
public class InitCache implements ApplicationRunner {

    @Autowired
    private MdmFeignClient mdmFeignClient;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    @Qualifier("incloudProcessCache")
    IncloudCache<WfProcess> incloudCache;

    @Autowired
    @Qualifier("incloudUserCache")
    IncloudCache<MdmUserVo> userIncloudCache;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<WfProcess> list = wfProcessService.list();
        incloudCache.puts(list, WfProcess::getId, WfProcess::getCamundaProcinsId);
        log.info("初始化WfProcess缓存完成！");
        initUserCache();
    }

    private void initUserCache() {
        log.info("开除初始化用户信息Start");
        List<MdmUserVo> allUserList = mdmFeignClient.getAllUserList();
        log.info("获取到用户信息:{},存储到缓存中，Id和UserName各一份", allUserList.size());
        userIncloudCache.puts(allUserList, MdmUserVo::getId, MdmUserVo::getUserName);
        log.info("初始化用户信息完成End");
    }

}
