package com.nhuy.orderservice.infrastructure.persistence.jpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;


import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter
@Setter
public class OutboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aggregateType; // "Order"
    private UUID aggregateId;
    private String eventType;     // "OrderPlaced"


    @ColumnTransformer(write = "?::jsonb")
    @Column(columnDefinition = "jsonb")
    private String payload;
    private OffsetDateTime occurredAt;
    private boolean processed;
    private String dedupKey; //
}
