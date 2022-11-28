package com.netwisd.base.model.controller.api;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.model.dto.DbDsDto;
import com.netwisd.base.model.service.DbDsService;
import com.netwisd.base.model.service.TableService;
import com.netwisd.common.core.exception.IncloudException;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "DbDsApi", tags = "数据源内部ApiController")
public class DbDsApiController {

    private final TableService tableService;

    private final DbDsService dbDsService;

    @PostMapping("/exec")
    public List<Map> exec(@RequestBody DbDsDto dbDsDto) {
        String dbds = dbDsDto.getDbds();
        if (StrUtil.isEmpty(dbds)) {
            dbds = Optional.ofNullable(dbDsService.getById(dbDsDto.getDbDsId()))
                    .orElseThrow(() -> new IncloudException("未找到要执行的数据源"))
                    .getPoolName();
        }
        return tableService.execSql(dbds, dbDsDto.getSql());
    }

}
