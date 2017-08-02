package com.github.btr.demo.domain.entity;

import com.github.btr.demo.infrastructure.util.IdWorker;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 通话记录聚合根
 */
@Data
@Document(collection = "call_up_record1")
public class CallUpRecord
{
	@Id
	private String id;
	private String call;
	private String to;
	private Long   maxCallTime;
	private String messageId;
	@CreatedDate
	private Date   createTime;
	@LastModifiedDate
	private Date   updateTime;

	public CallUpRecord(final String call, final String to, final Long maxCallTime)
	{
		this.id = IdWorker.getFlowIdWorkerInstance().nextSID();
		this.call = call;
		this.to = to;
		this.maxCallTime = maxCallTime;
	}
}
