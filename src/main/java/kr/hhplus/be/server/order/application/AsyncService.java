package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.OrderEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final OrderRedisRepository orderRedisRepository;

    @Async
    @TransactionalEventListener
    public void executedWriteOrderQuantity(OrderEventDTO.OrderItemRank orderItemRank) {
        orderRedisRepository.addOrderItemListQuantityToRankDaily(
            orderItemRank.getOrderItems().stream()
                .map(item -> OrderEventDTO.OrderItemEventDTO.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .build())
                .toList()
        );
    }
}
