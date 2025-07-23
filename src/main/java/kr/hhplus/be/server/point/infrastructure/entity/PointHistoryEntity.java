package kr.hhplus.be.server.point.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.point.common.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointHistoryId;

    private Long userId;
    private Long pointId;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;
}


