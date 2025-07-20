package kr.hhplus.be.server;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.util.List;

public class MockDTO {

    public static class ResponseGetPayMoney {
        public Long balance = 10000L;
        public Long userId = 1L;
    }


    public static class RequestChargePayMoney {
        public Long userId;
        public Long amount;
    }

    public static class ResponseChargePayMoney {
        public Long balance = 10000L;
        public Long userId = 1L;
    }

    public static class ResponseGetProduct {
        public Long productId = 1234L;
        public String name = "구글엔지니어는 이렇게 일한다";
        public Long price = 1000L;
        public String description = "샘플 설명";
        public String category = "IT";
        public String registeredAt = "2023-10-01T12:00:00Z";
        public String updatedAt = "2023-10-01T12:00:00Z";
        public int quantity = 10;
        public String states = "AVAILABLE"; // AVAILABLE, SOLD_OUT
    }

    public static class RequestIssueCoupon {
        public Long userId;
        public String couponCode;
    }

    public static class ResponseIssueCoupon {
        public Long userId;
        public String coupon_code;
        public String issuedAt = "2023-10-01T12:00:00Z";
        public String expiredAt = "2023-12-31";
        public String status = "ACTIVE";
    }


    /**
     * MockAPI 일때에만 defaultValue 존재합니다
     */
    public static class RequestCreateOrder {
        @NotNull(message = "유저 ID는 필수입니다.")
        @Schema(description = "유저 ID", example = "1234", defaultValue = "1234")
        public Long userId;

        @NotNull(message = "결제 금액은 필수입니다.")
        @Schema(description = "결제 금액", example = "7000", defaultValue = "7000")
        public Long paidAmount;

        @NotNull(message = "주문 금액은 필수입니다.")
        @Schema(description = "주문 금액", example = "10000", defaultValue = "10000")
        public Long orderedAmount;

        @Schema(description = "할인 금액", example = "3000", defaultValue = "3000")
        public Long discountAmount;

        @NotNull(message = "쿠폰 사용 여부")
        @Schema(description = "쿠폰 사용 여부", example = "false", defaultValue = "false")
        public Boolean isCouponUsed;

        @Schema(description = "쿠폰 ID", example = "555", defaultValue = "555")
        public Long couponId;

        @Schema(description = "주문 상품 목록")
        List<orderedProduct> orderedProducts;
    }
    @AllArgsConstructor
    public static class orderedProduct{
        @Schema(description = "상품 ID", example = "111231", defaultValue = "111231")
        public Long productId;

        @Schema(description = "상품명", example = "구글엔지니어는 이렇게 일한다", defaultValue = "구글엔지니어는 이렇게 일한다")
        public String productName;

        @Schema(description = "상품 가격", example = "23000", defaultValue = "23000")
        public Long price;

        @Schema(description = "수량", example = "2", defaultValue = "2")
        public Long quantity;

        @Schema(description = "카테고리", example = "IT", defaultValue = "IT")
        public String category;
    }

    public static class ResponseCreateOrder {
        public Long orderId = 5124L;
        public Long userId = 1231L;
        public Long paidAmount = 7000L;
        public Long orderedAmount = 10000L;
        public Long discountAmount = 3000L;
        public String orderStatus = "COMPLETED";
        public String couponStatus = "USED";
    }

    public static class ResponseGetTop5Products {
        public List<TopProduct> topProducts = List.of(
                new TopProduct(789012L, "클린 코드", 20000L, 34, "IT"),
                new TopProduct(111231L, "구글엔지니어는 이렇게 일한다", 23000L, 25, "IT"),
                new TopProduct(152321L, "채식주의", 12000L, 22, "NOVEL"),
                new TopProduct(123456L, "자바 프로그래밍", 15000L, 11, "IT"),
                new TopProduct(654321L, "파이썬 입문", 18000L, 4, "IT")
        );

        @AllArgsConstructor
        public static class TopProduct {
            public Long productId;
            public String name;
            public Long totalSales;
            public int quantitySold;
            public String category;
        }
    }


}
