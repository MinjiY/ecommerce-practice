//package kr.hhplus.be.server.order.infrastructure.entity;
//
//import jakarta.persistence.*;
//import kr.hhplus.be.server.order.common.PaymentStatus;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//
//@Entity
//@Table(name = "payment_history")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PaymentHistoryEntity extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long paymentHistoryId;
//
//    private Long userId;
//    private Long totalAmount;
//    private Long discountAmount;
//    private Long paidAmount;
//    private Long couponId;
//
//    @Enumerated(EnumType.STRING)
//    private PaymentStatus paymentStatus;
//
//    @Builder
//    public PaymentHistoryEntity(Long userId, Long totalAmount, Long discountAmount,Long paidAmount, Long couponId, PaymentStatus paymentStatus) {
//        this.userId = userId;
//        this.totalAmount = totalAmount;
//        this.discountAmount = discountAmount;
//        this.paidAmount = paidAmount;
//        this.couponId = couponId;
//        this.paymentStatus = paymentStatus;
//    }
//
//
//}
