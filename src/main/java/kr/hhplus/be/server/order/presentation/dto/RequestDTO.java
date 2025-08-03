package kr.hhplus.be.server.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.AllArgsConstructor;

import java.util.List;

public class RequestDTO {

    @AllArgsConstructor
    public static class CreateOrderRequest {
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
        List<CreateOrderItemRequest> orderedProducts;

        public OrderCommandDTO.CreateOrderCommand toCommand() {
            return OrderMapper.INSTANCE.requestToCommand(this)
                    .setOrderedProducts(
                            this.orderedProducts.stream().map(CreateOrderItemRequest::toCommand)
                        .toList());
        }
    }

    @AllArgsConstructor
    public static class CreateOrderItemRequest{
        @NotNull(message = "유저 ID는 필수입니다.")
        @Schema(description = "유저 ID", example = "1234", defaultValue = "1234")
        public Long userId;

        @NotNull(message = "상품명은 필수입니다.")
        @Schema(description = "상품명", example = "상품 A", defaultValue = "상품 A")
        public String productName;

        @NotNull(message = "상품 금액은 필수입니다.")
        @Schema(description = "상품 금액", example = "10000", defaultValue = "10000")
        public Long productAmount;

        @NotNull(message = "주문 수량은 필수입니다.")
        @Schema(description = "주문 수량", example = "2", defaultValue = "2")
        public Integer orderQuantity;

        @NotNull(message = "상품 ID는 필수입니다.")
        @Schema(description = "상품 ID", example = "1", defaultValue = "1")
        public Long productId;

        public OrderCommandDTO.CreateOrderItemCommand toCommand() {
            return OrderMapper.INSTANCE.requestToCommand(this);
        }
    }


}

