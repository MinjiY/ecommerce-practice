package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.OrderEventDTO;

import java.util.List;

public interface OrderRedisRepository {

    Double addOrderItemQuantityToRankDaily(String productId, Double quantity);
    void addOrderItemListQuantityToRankDaily(List<OrderEventDTO.OrderItemEventDTO> orderItemRankDTOList);
}
