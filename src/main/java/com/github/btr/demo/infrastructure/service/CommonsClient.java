package com.github.btr.demo.infrastructure.service;

import com.alibaba.fastjson.JSON;
import com.github.btr.demo.interfaces.dto.CallUpDTO;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

/**
 * 泓华公用模块基础服务
 */
@Component
public class CommonsClient
{
	private final WebClient client;

	public CommonsClient()
	{
		this.client = WebClient.builder()
										//使用响应式异步Netty
										.clientConnector(new ReactorClientHttpConnector())
										//泓华公用模块
										.baseUrl("https://wechat.oasiscare.cn/commons/v2/")
										.build();
	}

	/**
	 * 异步Get请求
	 * @param uri         请求地址
	 * @param queryString 请求参数
	 * @return
	 */
	private Mono<String> get(final String uri, final String... queryString)
	{
		//queryString处理
		return Flux.fromArray(queryString)
						 //日志打印
						 .log()
						 //把queryString数组规约成一个字符串输出
						 .reduce((x, y) -> x + "&" + y)
						 //转换 uri?queryString拼接
						 .map(s -> uri + "?" + s)
						 //如果queryString没有则直接取原访问地址
						 .defaultIfEmpty(uri)
						 /**
							* flatMap = flatten + map
							* 简单理解为map拆包
							* example
							* Mono<String> m1 = Mono.just("s");
							* Mono<String> m2 = Mono.just("s");
							* Mono<Mono<String>> m3 = m1.map(s -> m1);
							* Mono<String> m4 = m1.flatMap(s -> m1);
							*/
						 .flatMap(s -> client.get()
														 .uri(s)
														 .ifNoneMatch("*")
														 .ifModifiedSince(ZonedDateTime.now())
														 .retrieve()
														 .bodyToMono(String.class));
	}

	/**
	 * 异步Post请求
	 * @param uri  请求地址
	 * @param data 请求数据
	 * @param <T>
	 * @return
	 */
	private <T> Mono<String> post(final String uri, final T data)
	{
		return client.post()
						 .uri(uri)
						 .contentType(MediaType.APPLICATION_JSON_UTF8)
						 .ifNoneMatch("*")
						 .ifModifiedSince(ZonedDateTime.now())
						 .body(Mono.just(JSON.toJSONString(data)),String.class)
						 .retrieve()
						 .bodyToMono(String.class);
	}

	/**
	 * 打电话
	 * @param dto
	 * @return
	 */
	public Mono<String> callUp(final CallUpDTO dto)
	{
		return post("7moor/call-up", dto);
	}
}
