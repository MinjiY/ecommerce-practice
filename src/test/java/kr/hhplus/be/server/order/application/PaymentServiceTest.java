//package kr.hhplus.be.server.order.application;
//
//import kr.hhplus.be.server.order.common.PaymentStatus;
//import kr.hhplus.be.server.order.domain.PaymentHistory;
//import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PaymentServiceTest {
//
//    @InjectMocks
//    private PaymentService paymentService;
//
//    @Mock
//    private PaymentHistoryRepository paymentHistoryRepository;
//
//    @DisplayName("결제 처리 메소드가 호출되면, PaymentHistory를 저장하고 결과를 반환한다.")
//    @Test
//    void processPayment() {
//        // given
//        long userId = 123L;
//        long totalAmount = 5000L;
//        long discountAmount = 0L;
//        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
//        PaymentHistory paymentHistory = PaymentHistory.builder()
//                .userId(userId)
//                .totalAmount(totalAmount)
//                .discountAmount(discountAmount)
//                .paymentStatus(paymentStatus)
//                .build();
//        when(paymentHistoryRepository.save(any(PaymentHistory.class))).thenReturn(paymentHistory);
//
//        OrderCommandDTO.PaymentCommand paymentCommand = OrderCommandDTO.PaymentCommand.builder()
//                .userId(userId)
//                .totalAmount(totalAmount)
//                .discountAmount(discountAmount)
//                .paymentStatus(paymentStatus)
//                .build();
//
//        // when
//        OrderCommandDTO.PaymentHistoryResult result = paymentService.processPayment(paymentCommand);
//
//        // then
//        verify(paymentHistoryRepository).save(any(PaymentHistory.class));
//        assertAll(
//            () -> assertNotNull(result, "결제 결과가 null이 아닙니다."),
//            () -> assertEquals(paymentHistory.getUserId(), result.getUserId(), "사용자 ID가 일치합니다."),
//            () -> assertEquals(paymentHistory.getTotalAmount(), result.getTotalAmount(), "총 결제 금액이 일치합니다."),
//            () -> assertEquals(paymentHistory.getPaymentStatus(), result.getPaymentStatus(), "결제 상태가 일치합니다.")
//        );
//    }
//
//}