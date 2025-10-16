package com.nhuy.orderservice.infrastructure.persistence.outbox;

import com.nhuy.orderservice.infrastructure.persistence.jpa.OutboxEntity;
import jakarta.persistence.LockModeType;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from OutboxEntity  o where o.processed = false order by o.occurredAt")
    List<OutboxEntity> findBatch(PageRequest pageRequest);
}
