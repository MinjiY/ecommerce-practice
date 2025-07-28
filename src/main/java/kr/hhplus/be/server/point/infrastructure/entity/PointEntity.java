package kr.hhplus.be.server.point.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    private Long balance;
    private Long userId;

    @Builder
    public PointEntity(Long balance, Long userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public void chargeAmount(Long balance) {
        this.balance += balance;
    }
}
