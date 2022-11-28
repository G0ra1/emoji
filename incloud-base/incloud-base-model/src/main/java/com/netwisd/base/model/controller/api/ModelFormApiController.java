package com.netwisd.base.model.controller.api;

import com.netwisd.base.model.service.ModelFormService;
import com.netwisd.base.common.model.vo.ModelFormVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "ModelFormApi", tags = "表达管理ApiController")
public class ModelFormApiController {

    private ModelFormService modelFormService;

    @GetMapping("/formDetail/{id}")
    public ModelFormVo formDetailById(@PathVariable(value = "id") String id) {
        return modelFormService.getModelFormDetailByEasy(id);
    }

    @GetMapping("/formDetailByFormName/{formName}")
    public ModelFormVo formDetailByFormName(@PathVariable(value = "formName") String formName) {
        return modelFormService.getModelFormDetailByFormName(formName);
    }

}
