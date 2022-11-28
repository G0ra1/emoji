package com.netwisd.base.wf.handler;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.common.db.cache.IncloudCache;
import com.netwisd.common.msg.rocket.data.BinLogData;
import com.netwisd.common.msg.rocket.service.BinLogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("incloud_base_mdm_user")
public class MdmUserHandler implements BinLogDataService<BinLogData<MdmUserVo>> {

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> userIncloudCache;

    @Override
    public void handle(String binLog) {
        log.info("incloud_base_mdm_user获取到消息内容是:{}", binLog);
        BinLogData<MdmUserVo> binlogData = getBinLogData(binLog);
        List<MdmUserVo> datas = binlogData.getData();
        switch (binlogData.getType()) {
            case "INSERT":
            case "UPDATE":
                userIncloudCache.puts(datas, MdmUserVo::getId, MdmUserVo::getUserName);
                break;
            case "DELETE":
                datas.forEach(x -> userIncloudCache.del(x.getId(), x.getUserName()));
                break;
        }
    }
}
