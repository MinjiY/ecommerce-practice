package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.domain.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private PaymentHistoryRepository paymentHistoryRepository;

    // 결제 처리 메소드
    public OrderCommandDTO.PaymentHistoryResult processPayment(OrderCommandDTO.PaymentCommand paymentCommand) {
        // 결제 정보로 PaymentHistory 생성
        PaymentHistory paymentHistory = paymentCommand.toDomain();
        return OrderCommandDTO.PaymentHistoryResult.from(paymentHistoryRepository.save(paymentHistory));
    }


}
