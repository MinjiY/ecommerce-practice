package kr.hhplus.be.server.point.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.point.common.TransactionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointHistoryId;

    private Long userId;
    private Long pointId;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Builder
    public PointHistoryEntity(Long userId, Long pointId, Long amount, TransactionType transactionType) {
        this.userId = userId;
        this.pointId = pointId;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}


