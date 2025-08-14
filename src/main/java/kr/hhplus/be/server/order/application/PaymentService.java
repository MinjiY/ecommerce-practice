//package kr.hhplus.be.server.order.application;
//
//import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
//import kr.hhplus.be.server.order.domain.PaymentHistory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class PaymentService {
//
//    private final PaymentHistoryRepository paymentHistoryRepository;
//
//    // 결제 처리 메소드
//    public OrderCommandDTO.PaymentHistoryResult processPayment(OrderCommandDTO.PaymentCommand paymentCommand) {
//        // 결제 정보로 PaymentHistory 생성
//        log.info("Processing payment for userId: {}, amount: {}", paymentCommand.getUserId(), paymentCommand.getPaidAmount());
//        log.info("Total amount: {}", paymentCommand.getTotalAmount());
//        log.info("Discount amount: {}", paymentCommand.getDiscountAmount());
//        log.info("Paid amount: {}", paymentCommand.getPaidAmount());
//
//        PaymentHistory paymentHistory = paymentCommand.toDomain();
//        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistory);
//        log.info("Payment history saved with ID: {}", savedPaymentHistory.getPaymentHistoryId());
//        log.info("Paid amount: {}", savedPaymentHistory.getPaidAmount());
//        return OrderCommandDTO.PaymentHistoryResult.from(savedPaymentHistory);
//    }
//
//
//}
