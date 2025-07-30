package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> saveAll(Order order, List<OrderItem> orderItems);
}
