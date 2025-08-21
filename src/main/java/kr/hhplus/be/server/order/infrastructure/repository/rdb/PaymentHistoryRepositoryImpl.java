//package kr.hhplus.be.server.order.infrastructure.repository;
//
//import kr.hhplus.be.server.order.application.PaymentHistoryRepository;
//import kr.hhplus.be.server.order.domain.PaymentHistory;
//import kr.hhplus.be.server.order.infrastructure.entity.PaymentHistoryEntity;
//import kr.hhplus.be.server.order.mapper.OrderMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class PaymentHistoryRepositoryImpl implements PaymentHistoryRepository {
//    private final PaymentHistoryJpaRepository PaymentHistoryJpaRepository;
//
//    @Override
//    public PaymentHistory save(PaymentHistory paymentHistory) {
//        return OrderMapper.INSTANCE.entityToPaymentHistoryDomain(PaymentHistoryJpaRepository.save(OrderMapper.INSTANCE.domainToPaymentHistoryEntity(paymentHistory)));
//    }
//
//}
