package kr.hhplus.be.server.order.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.hhplus.be.server.order.common.PaymentStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
//import kr.hhplus.be.server.order.domain.PaymentHistory;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderCommandDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateOrderCommand {
        private Long userId;            // 주문을 생성하는 사용자 ID

        private Long orderedAmount;     // 주문 금액

        private Long discountAmount;    // 할인 금액

        private Long paidAmount;        // 결제 금액

        @Builder.Default
        private Boolean isCouponUsed = false;   // 쿠폰 사용 여부, 기본값은 false

        private Long couponId;

        private List<CreateOrderItemCommand> orderedProducts;

        public CreateOrderCommand setOrderedProducts(List<CreateOrderItemCommand> orderedProducts) {
            this.orderedProducts = orderedProducts;
            return this;
        }

//        public PaymentCommand toPaymentCommand() {
//            return PaymentCommand.builder()
//                    .userId(this.userId)
//                    .totalAmount(this.orderedAmount)
//                    .discountAmount(this.discountAmount)
//                    .paidAmount(this.paidAmount)
//                    .couponId(this.isCouponUsed ? this.couponId : null)
//                    .build();
//        }

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

        private Long paidAmount;        // 결제 금액, 실제 결제된 금액

        private LocalDate orderDate;
        private LocalDate updateDate;
        private LocalDateTime orderedAt;
        private LocalDateTime updatedAt;

        // -- pay
//        private Long paymentHistoryId; // 결제 기록 ID, 생성된 결제 기록의 ID
//        private PaymentStatus paymentStatus; // 결제 상태

        private List<CreateOrderItemResult> orderedProducts;

//        public void setPaymentHistoryId(Long paymentHistoryId) {
//            this.paymentHistoryId = paymentHistoryId;
//        }
//        public void setPaymentStatus(PaymentStatus paymentStatus) {
//            this.paymentStatus = paymentStatus;
//        }

        public static CreateOrderResult from(Order order, List<OrderItem> orderItems) {

            return CreateOrderResult.builder()
                    .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderedAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .paidAmount(order.getPaidAmount())
                    .orderDate(order.getOrderDate())
                .updateDate(order.getUpdateDate())
                .orderedAt(order.getOrderedAt())
                .updatedAt(order.getUpdatedAt())
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

//    @Getter
//    @Builder
//    @AllArgsConstructor
//    public static class PaymentCommand {
//        private Long userId;
//        private Long totalAmount;
//        private Long discountAmount;
//        private Long paidAmount; // 결제 금액, 실제 결제된 금액
//        private Long couponId;
//
//        @Builder.Default
//        private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
//
//        public PaymentHistory toDomain(){
//            return PaymentHistory.builder()
//                    .userId(this.userId)
//                    .totalAmount(this.totalAmount)
//                    .discountAmount(this.discountAmount)
//                    .paidAmount(this.paidAmount)
//                    .couponId(this.couponId)
//                    .paymentStatus(this.paymentStatus)
//                    .build();
//        }
//    }
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public static class PaymentHistoryResult {
//        private Long paymentHistoryId; // 결제 기록 ID, 생성된 결제 기록의 ID
//        private Long userId;
//        private Long totalAmount;
//        private Long discountAmount;
//        private Long paidAmount; // 결제 금액, 실제 결제된 금액
//        private Long couponId;
//        private PaymentStatus paymentStatus;
//
//        public static PaymentHistoryResult from(PaymentHistory paymentHistory) {
//            return PaymentHistoryResult.builder()
//                    .paymentHistoryId(paymentHistory.getPaymentHistoryId())
//                    .userId(paymentHistory.getUserId())
//                    .totalAmount(paymentHistory.getTotalAmount())
//                    .discountAmount(paymentHistory.getDiscountAmount())
//                    .paidAmount(paymentHistory.getPaidAmount())
//                    .couponId(paymentHistory.getPaymentStatus() == PaymentStatus.DISCOUNT ? paymentHistory.getCouponId() : null)
//                    .paymentStatus(paymentHistory.getPaymentStatus())
//                    .build();
//        }
//    }
}
