package com.netwisd.base.msg.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.ExceptionUtil;
import com.netwisd.base.msg.dto.ProducerDto;
import com.netwisd.base.msg.entity.Producer;
import com.netwisd.base.msg.mapper.ProducerMapper;
import com.netwisd.base.msg.mq.ProducerFactory;
import com.netwisd.base.msg.service.ProducerService;
import com.netwisd.base.msg.vo.ProducerVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProducerServiceImpl extends ServiceImpl<ProducerMapper, Producer> implements ProducerService {

    @Autowired
    private Mapper dozerMapper;

    @Override
    public IPage queryPageList(ProducerDto producerDto) {
        return page(producerDto.getPage(), Wrappers.<Producer>lambdaQuery()
                .eq(StrUtil.isNotEmpty(producerDto.getMq()), Producer::getMq, producerDto.getMq())
                .like(StrUtil.isNotEmpty(producerDto.getTopic()), Producer::getTopic, producerDto.getTopic())
                .like(StrUtil.isNotEmpty(producerDto.getHost()), Producer::getHost, producerDto.getHost())
                .like(ObjectUtil.isNotEmpty(producerDto.getPort()), Producer::getHost, producerDto.getPort())
                .like(ObjectUtil.isNotEmpty(producerDto.getVirtualHost()), Producer::getVirtualHost, producerDto.getVirtualHost())
                .orderByDesc(Producer::getCreateTime));
    }

    @Override
    public ProducerVo getProduer(String id) {
        Producer producer = getById(id);
        return ObjectUtil.isNull(producer) ? null : dozerMapper.map(producer, ProducerVo.class);
    }

    @Override
    @Transactional
    public boolean saveProducer(ProducerDto producerDto) {
        Optional.of(count(Wrappers.<Producer>lambdaQuery().eq(Producer::getMq, producerDto.getMq())
                .eq(Producer::getHost, producerDto.getHost()).eq(Producer::getPort, producerDto.getPort())
                .eq(Producer::getVirtualHost, producerDto.getVirtualHost()).eq(Producer::getProducerGroup, producerDto.getProducerGroup()))
        ).filter(x -> {
            if (x > 0) {
                throw new IncloudException("已存在");
            }
            return true;
        });
        boolean save = save(dozerMapper.map(producerDto, Producer.class));
        //创建对应的MQ实例信息
        if (save && 0 == producerDto.getStatus()) {
            try {
                ProducerFactory.getProducer(producerDto.toProducerConifg(), producerDto.getMq()).start();
            } catch (Exception e) {
                log.error("创建{}的MQ实例信息失败:{}", producerDto.getMq(), ExceptionUtil.getExceptionMessage(e));
                throw new IncloudException(e);
            }
        }
        return save;
    }

    @Override
    public boolean changeStatus(String id) {
        Producer producer = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("为获取到对应的信息"));
        Integer state = producer.getStatus() == 0 ? 1 : 0;//（0正常 1暂停)
        producer.setStatus(state);
        boolean update = updateById(producer);
        if (update) {
            ProducerFactory producerFactory = ProducerFactory.getProducer(producer.toProducerConifg(), producer.getMq());
            try {
                if (update && state == 0) {//正常
                    producerFactory.start();
                } else if (update && state == 1) { //暂停
                    producerFactory.shutdown();
                }
            } catch (Exception e) {
                log.error("修改{}的MQ实例信息失败:{}", producer.getMq(), ExceptionUtil.getExceptionMessage(e));
                throw new IncloudException(e);
            }
        }
        return update;
    }

    @Override
    public boolean send(String id) {
        Producer producer = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未找到对应的生产者"));
        ProducerFactory producerFactory = ProducerFactory.getProducer(producer.toProducerConifg(), producer.getMq());
        try {
            producerFactory.send(null);
        } catch (Exception e) {
            log.error("生产者发送失败:{},{},{}", producer.getMq(), id, ExceptionUtil.getExceptionMessage(e));
            throw new IncloudException(e);
        }
        return false;
    }

    @Override
    public boolean upProducer(ProducerDto producerDto) {
        Producer producer = dozerMapper.map(producerDto, Producer.class);
        boolean update = updateById(producer);
        if (update) {
            try {
                //先移除在重新创建
                ProducerFactory producerFactory = ProducerFactory.getProducer(producerDto.toProducerConifg(), producerDto.getMq());
                producerFactory.shutdown();
                if (0 == producerDto.getStatus()) {//正常
                    producerFactory.start();
                }
            } catch (Exception e) {
                log.error("创建{}的MQ实例信息失败:{}", producerDto.getMq(), ExceptionUtil.getExceptionMessage(e));
                throw new IncloudException(e);
            }
        }
        return update;
    }

    @Override
    public boolean removeProducer(String id) {
        Producer producer = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未找到要删除的信息"));
        boolean remove = removeById(id);
        if (remove) {
            try {
                ProducerFactory.getProducer(producer.toProducerConifg(), producer.getMq()).shutdown();
            } catch (Exception e) {
                log.error("删除{}的MQ实例信息失败:{}", producer.getMq(), ExceptionUtil.getExceptionMessage(e));
                throw new IncloudException(e);
            }
        }
        return remove;
    }
}
