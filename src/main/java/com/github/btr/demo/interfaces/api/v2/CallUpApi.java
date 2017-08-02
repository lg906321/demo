package com.github.btr.demo.interfaces.api.v2;

import com.github.btr.demo.application.service.CallUpService;
import com.github.btr.demo.interfaces.dto.CallUpDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 打电话接口
 */
@Slf4j
@RestController
@RequestMapping("v2/call")
public class CallUpApi
{
	private final CallUpService service;

	public CallUpApi(final CallUpService service)
	{
		this.service = service;
	}

	/**
	 * curl -H "Content-Type: application/json;charset=UTF-8" -H "Accept:application/json;charset=UTF-8" -X POST -d '{
	 * "callMobile":"18516966501",
	 * "callToMobile":"18516966502",
	 * "maxCallTime":30}' -v localhost:8080/v2/call
	 * @param data
	 * @return
	 */
	@PostMapping
	public Mono<ResponseEntity> call(@RequestBody final Mono<CallUpDTO> data)
	{
		log.info("打电话接口");

		return data
						 //调用打电话应用服务
						 .flatMap(service::callUp)
						 .map(d -> ResponseEntity.ok()
												 .contentType(MediaType.APPLICATION_JSON_UTF8)
												 .cacheControl(CacheControl.noCache())
												 .body(d));
	}

	/**
	 * curl -H "Content-Type: application/json;charset=UTF-8" -H "Accept:application/json;charset=UTF-8" -v localhost:8080/v2/call/{id}
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CallUpDTO>> get(@PathVariable String id)
	{
		log.info("查询通话记录 =====> {}",id);

		return service.getById(id)
						 //查询到了则返回200 OK
						 .map(d -> ResponseEntity.ok()
												 .contentType(MediaType.APPLICATION_JSON_UTF8)
												 .cacheControl(CacheControl.noCache())
												 .body(d))
						 //不存在返回404 NotFound
						 .defaultIfEmpty(ResponseEntity.notFound()
															 .cacheControl(CacheControl.noCache())
															 .build());
	}
}
