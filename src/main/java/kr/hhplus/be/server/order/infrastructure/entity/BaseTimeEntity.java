package kr.hhplus.be.server.order.infrastructure.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDate orderDate;

    @LastModifiedDate
    private LocalDate updateDate;

    @CreatedDate
    private LocalDateTime orderedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
