package com.github.btr.demo.application.service;

import com.github.btr.demo.interfaces.dto.CallUpDTO;
import reactor.core.publisher.Mono;

/**
 * 打电话应用服务
 */
public interface CallUpService
{
	/**
	 * 打电话
	 * @param data
	 */
	Mono<CallUpDTO> callUp(CallUpDTO data);

	/**
	 * ID查询通话记录
	 * @param id
	 * @return
	 */
	Mono<CallUpDTO> getById(String id);
}
