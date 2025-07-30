package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.application.PaymentHistoryRepository;
import kr.hhplus.be.server.order.domain.PaymentHistory;
import kr.hhplus.be.server.order.infrastructure.entity.PaymentHistoryEntity;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class PaymentHistoryRepositoryImpl implements PaymentHistoryRepository {
    private final JpaRepository<PaymentHistoryEntity, Long> PaymentHistoryJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public PaymentHistory save(PaymentHistory paymentHistory) {
        return orderMapper.entityToPaymentHistoryDomain(PaymentHistoryJpaRepository.save(orderMapper.domainToPaymentHistoryEntity(paymentHistory)));
    }

}
