package com.github.btr.demo.application.service.impl;

import com.github.btr.demo.application.service.CallUpService;
import com.github.btr.demo.domain.entity.CallUpRecord;
import com.github.btr.demo.infrastructure.repository.CallUPRecordRepository;
import com.github.btr.demo.infrastructure.service.CommonsClient;
import com.github.btr.demo.interfaces.assembler.CallUpAssembler;
import com.github.btr.demo.interfaces.dto.CallUpDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * 打电话应用服务实现类
 * 在此调用领域服务、基础服务,实现本地事务
 */
@Service
@Transactional
public class CallUpServiceImpl implements CallUpService
{
	private final CommonsClient          client;
	private final CallUPRecordRepository persistence;

	public CallUpServiceImpl(final CommonsClient client, final CallUPRecordRepository persistence)
	{
		this.client = client;
		this.persistence = persistence;
	}

	@Override
	public Mono<CallUpDTO> callUp(final CallUpDTO data)
	{
		return Mono.just(data)
						 //创建通话记录
						 .map(d -> new CallUpRecord(d.callMobile, d.callToMobile, d.maxCallTime))
						 //插入数据库
						 .flatMap(persistence::insert)
						 //异步http请求接口打电话
						 .flatMap(d -> client.callUp(data)
														 //更新通话记录 记录通话唯一标识
														 .map(s ->
														 {
															 d.setMessageId(s);
															 return d;
														 }))
						 //更新数据库
						 .flatMap(persistence::save)
						 //转换成DTO返回
						 .map(CallUpAssembler::toDTO);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Mono<CallUpDTO> getById(final String id)
	{
		return persistence.findByMessageId(id).map(CallUpAssembler::toDTO);
	}
}
