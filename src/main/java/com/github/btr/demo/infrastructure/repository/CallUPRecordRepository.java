package com.github.btr.demo.infrastructure.repository;

import com.github.btr.demo.domain.entity.CallUpRecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * 通话记录持久化
 * 使用响应式Mongodb作为数据库
 */
public interface CallUPRecordRepository extends ReactiveMongoRepository<CallUpRecord, String>
{
	Mono<CallUpRecord> findByMessageId(String messageId);
}
