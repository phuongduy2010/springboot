package com.nhuy.orderservice.infrastructure.messaging;
import com.nhuy.orderservice.infrastructure.persistence.jpa.OutboxEntity;
import com.nhuy.orderservice.infrastructure.persistence.outbox.OutboxJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class OutboxDispatcher {
    private final OutboxJpaRepository repo;
    private final KafkaTemplate<String, String> kafka;
    private final TransactionTemplate tx;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public OutboxDispatcher(OutboxJpaRepository repo,
                            KafkaTemplate<String,String> kafka,
                            @Qualifier("jpaTransactionTemplate") TransactionTemplate txTemplate) {
        this.repo = repo;
        this.kafka = kafka;
        this.tx = txTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void flush() {
        if(!this.running.compareAndSet(false,true)){
//            log.info("flushing outbox........");
            return;
        }
        try {
//            log.info("Start flushing outbox");
            List<OutboxEntity> batch = tx.execute(status -> repo.findBatch(PageRequest.of(0, 50)));
            if (batch == null || batch.isEmpty()) {
                return;
            }
            for (var e : batch) {
                kafka.executeInTransaction(t -> {
                    log.info("Start sending out outbox");
                    t.send("order-events", e.getAggregateId().toString(), e.getPayload());
                    return true;
                });
                tx.executeWithoutResult(status -> {
                    e.setProcessed(true);
                    repo.save(e);
//                    log.info("End sending out outbox");
                });
            }
        }
        catch (Exception e) {
            log.error("Error while flushing outbox",e);
        }
        finally {
//            log.info("Start new flushing outbox");
            this.running.set(false);
        }
    }

}
