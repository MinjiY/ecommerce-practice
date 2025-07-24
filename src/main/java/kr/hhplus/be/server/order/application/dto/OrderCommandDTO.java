package kr.hhplus.be.server.order.application.dto;

import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderCommandDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateOrderCommand {
        private Long userId;            // 주문을 생성하는 사용자 ID

        private Long orderedAmount;     // 주문 금액

        private Long discountAmount;

        private Boolean isCouponUsed;

        private Long couponId;

        private List<CreateOrderItemCommand> orderedProducts;

        public CreateOrderCommand setOrderedProducts(List<CreateOrderItemCommand> orderedProducts) {
            this.orderedProducts = orderedProducts;
            return this;
        }

        public Order toDomain(){
            return OrderMapper.INSTANCE.dtoToDomain(this);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateOrderItemCommand{
        private Long userId;                    // 주문자
        private String productName;             // 상품명
        private Long productAmount;                     // 상품 금액
        private Integer orderQuantity;          // 주문 수량
        private Long productId;                 // 상품 ID

        public OrderItem toDomain() {
            return OrderMapper.INSTANCE.dtoToDomain(this);
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CreateOrderResult {
        private Long orderId;          // 주문 ID, 생성된 주문의 ID

        private Long userId;            // 주문을 생성하는 사용자 ID

        private Long orderedAmount;     // 주문 금액

        private Long discountAmount;

        private List<CreateOrderItemResult> orderedProducts;

        public static CreateOrderResult from(Order order, List<OrderItem> orderItems) {

            return CreateOrderResult.builder()
                    .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderedAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .orderedProducts(
                    orderItems.stream()
                        .map(OrderCommandDTO.CreateOrderItemResult::from)
                        .toList()
                )
                .build();
        }
    }

    @Getter
    @Builder
    public static class CreateOrderItemResult {
        private Long userId;                    // 주문자
        private String productName;             // 상품명
        private Long productAmount;                     // 상품 금액
        private Integer orderQuantity;          // 주문 수량
        private Long productId;                 // 상품 ID

        public static CreateOrderItemResult from(OrderItem orderItem) {
            return CreateOrderItemResult.builder()
                .userId(orderItem.getUserId())
                .productName(orderItem.getProductName())
                .productAmount(orderItem.getProductAmount())
                .orderQuantity(orderItem.getOrderQuantity())
                .productId(orderItem.getProductId())
                .build();
        }
    }

}
