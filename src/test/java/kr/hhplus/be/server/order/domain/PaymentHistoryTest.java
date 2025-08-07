//package kr.hhplus.be.server.order.domain;
//
//import kr.hhplus.be.server.exception.custom.OrderAmountMismatchException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PaymentHistoryTest {
//
//    @DisplayName("결제 금액이 주문 금액과 일치하지 않으면 OrderAmountMismatch 예외 발생")
//    @Test
//    void paymentAmountMismatch() {
//        Long paymentHistoryId = 1L;
//        Long userId = 1L;
//        Long totalAmount = 1000L;
//        Long discountAmount = 100L;
//        Long failedPaymentAmount = 900L; // 결제 금액이 주문 금액과 일치하지 않음
//        PaymentHistory paymentHistory =
//            PaymentHistory.builder()
//                    .paymentHistoryId(paymentHistoryId)
//                    .userId(userId)
//                    .totalAmount(totalAmount)
//                    .discountAmount(discountAmount)
//                    .build();
//
//
//        // when & then
//        assertThrows(OrderAmountMismatchException.class, () -> {
//            paymentHistory.paymentAmountMismatch(failedPaymentAmount);
//        });
//    }
//}