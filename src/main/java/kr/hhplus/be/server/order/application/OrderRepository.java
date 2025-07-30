package kr.hhplus.be.server.order.application;


import kr.hhplus.be.server.order.domain.Order;

public interface OrderRepository{
    Order save(Order order);
}
