package com.netwisd.base.msg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.msg.dto.ProducerDto;
import com.netwisd.base.msg.entity.Producer;
import com.netwisd.base.msg.vo.ProducerVo;

public interface ProducerService extends IService<Producer> {

    IPage queryPageList(ProducerDto producerDto);

    ProducerVo getProduer(String id);

    boolean saveProducer(ProducerDto producerDto);

    boolean changeStatus(String id);

    boolean send(String id);

    boolean upProducer(ProducerDto producerDto);

    boolean removeProducer(String id);

}
