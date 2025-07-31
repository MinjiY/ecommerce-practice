package kr.hhplus.be.server.user.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String accounts;

    @Builder
    public UserEntity(Long userId, String accounts) {
        this.userId = userId;
        this.accounts = accounts;
    }
}
