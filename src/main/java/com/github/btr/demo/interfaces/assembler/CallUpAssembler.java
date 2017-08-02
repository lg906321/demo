package com.github.btr.demo.interfaces.assembler;

import com.github.btr.demo.domain.entity.CallUpRecord;
import com.github.btr.demo.interfaces.dto.CallUpDTO;

/**
 * 通话记录聚合根与DTO转换器
 * 转换DTO是为了不暴露领域模型的领域行为
 */
public final class CallUpAssembler
{
	public static CallUpDTO toDTO(final CallUpRecord data)
	{
		return CallUpDTO.builder()
						 .callMobile(data.getCall())
						 .callToMobile(data.getTo())
						 .maxCallTime(data.getMaxCallTime())
						 .messageId(data.getMessageId())
						 .build();
	}
}
