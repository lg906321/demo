package com.github.btr.demo.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

/**
 * 打电话DTO
 * 使用Builder模式创建
 * 属性使用final来保证不变性
 */
@Builder
@ToString
public class CallUpDTO
{
	/**
	 * 拨打方
	 */
	public final String callMobile;
	/**
	 * 接通方
	 */
	public final String callToMobile;
	/**
	 * 通话时长
	 */
	public final Long   maxCallTime;
	/**
	 * 通话唯一标识
	 */
	public final String messageId;

	/**
	 * 使用JsonCreator指定JSON直接使用该构造方法进行对象的创建,避免创建空对象然后Set属性值.
	 * @param callMobile
	 * @param callToMobile
	 * @param maxCallTime
	 * @param messageId
	 */
	@JsonCreator
	public CallUpDTO(@JsonProperty("callMobile") final String callMobile, @JsonProperty("callToMobile") final String callToMobile,
									 @JsonProperty("maxCallTime") final Long maxCallTime, @JsonProperty("messageId") final String messageId)

	{
		this.callMobile = callMobile;
		this.callToMobile = callToMobile;
		this.maxCallTime = maxCallTime;
		this.messageId = messageId;
	}


}
