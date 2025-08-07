package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO.CreateOrderCommand;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public OrderCommandDTO.CreateOrderResult createOrder(CreateOrderCommand createOrderCommand) {
        List<OrderItem> orderItems = createOrderCommand.getOrderedProducts().stream()
                .map(OrderCommandDTO.CreateOrderItemCommand::toDomain)
                .toList();
        Order order = createOrderCommand.toDomain();
        order.orderedAmountMismatch(orderItems);

        // 주문 생성
        Order savedOrder = orderRepository.save(order);

        // 주문 아이템 저장
        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(savedOrder, orderItems);
        return OrderCommandDTO.CreateOrderResult.from(savedOrder, savedOrderItems);
    }
}
