package kr.hhplus.be.server.coupon.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestDTO {

    @Getter
    public static class cancelCouponRequest {
        @NotNull(message = "유저 ID는 필수입니다.")
        @Schema(description = "유저 ID", example = "1234")
        private Long userId;

        @NotNull(message = "쿠폰 ID는 필수입니다.")
        @Schema(description = "쿠폰 ID", example = "123")
        private Long couponId;

        public cancelCouponRequest(Long userId, Long couponId) {
            this.userId = userId;
            this.couponId = couponId;
        }

        public CouponCommandDTO.cancelCouponCommand toCommand() {
            return CouponCommandDTO.cancelCouponCommand.builder()
                    .userId(this.userId)
                    .couponId(this.couponId)
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor
    public static class IssueCouponRequest {
        @NotNull(message = "유저 ID는 필수입니다.")
        @Schema(description = "유저 ID", example = "1234")
        private Long userId;

        @NotNull(message = "쿠폰 ID는 필수입니다.")
        @Schema(description = "쿠폰 ID", example = "123")
        private Long couponId;

        public IssueCouponRequest(Long userId, Long couponId) {
            this.userId = userId;
            this.couponId = couponId;
        }

        public CouponCommandDTO.issueCouponCommand toCommand() {
            return CouponCommandDTO.issueCouponCommand.builder()
                    .userId(this.userId)
                    .couponId(this.couponId)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class useCouponRequest {
        @NotNull(message = "유저 ID는 필수입니다.")
        @Schema(description = "유저 ID", example = "1234")
        private Long userId;

        @NotNull(message = "쿠폰 ID는 필수입니다.")
        @Schema(description = "쿠폰 ID", example = "123")
        private Long couponId;

        public useCouponRequest(Long userId, Long couponId) {
            this.userId = userId;
            this.couponId = couponId;
        }

        public CouponCommandDTO.useCouponCommand toCommand() {
            return CouponCommandDTO.useCouponCommand.builder()
                    .userId(this.userId)
                    .couponId(this.couponId)
                    .build();
        }
    }
}
