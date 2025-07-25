package kr.hhplus.be.server.order.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.hhplus.be.server.order.common.PaymentStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.domain.PaymentHistory;
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

        public PaymentCommand toPaymentCommand() {
            return PaymentCommand.builder()
                    .userId(this.userId)
                    .totalAmount(this.orderedAmount)
                    .discountAmount(this.discountAmount)
                    .couponId(this.isCouponUsed ? this.couponId : null)
                    .build();
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

    @Builder
    @AllArgsConstructor
    public static class PaymentCommand {
        private Long userId;
        private Long totalAmount;
        private Long discountAmount;
        private Long couponId;

        @Builder.Default
        private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;

        public PaymentHistory toDomain(){
            return PaymentHistory.builder()
                    .userId(this.userId)
                    .totalAmount(this.totalAmount)
                    .discountAmount(this.discountAmount)
                    .couponId(this.couponId)
                    .paymentStatus(this.paymentStatus)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaymentHistoryResult {
        private Long userId;
        private Long totalAmount;
        private Long discountAmount;
        private Long couponId;
        private PaymentStatus paymentStatus;

        public static PaymentHistoryResult from(PaymentHistory paymentHistory) {
            return PaymentHistoryResult.builder()
                    .userId(paymentHistory.getUserId())
                    .totalAmount(paymentHistory.getTotalAmount())
                    .discountAmount(paymentHistory.getDiscountAmount())
                    .couponId(paymentHistory.getPaymentStatus() == PaymentStatus.DISCOUNT ? paymentHistory.getCouponId() : null)
                    .paymentStatus(paymentHistory.getPaymentStatus())
                    .build();
        }
    }



}
