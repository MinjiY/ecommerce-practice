package kr.hhplus.be.server.order.application.dto;

import kr.hhplus.be.server.order.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class OrderEventDTO {

    @Getter
    public static class OrderItemRank {
        private final List<OrderItemEventDTO> orderItems;

        @Builder
        public OrderItemRank(List<OrderItemEventDTO> orderItems) {
            this.orderItems = orderItems;
        }

        public static OrderItemRank from(OrderItem orderItem) {
            OrderItemEventDTO eventDTO = OrderItemEventDTO.builder()
                    .productId(orderItem.getProductId())
                    .quantity(orderItem.getOrderQuantity())
                    .build();

            return OrderItemRank.builder()
                    .orderItems(List.of(eventDTO))
                    .build();
        }

    }

    @Getter
    public static class OrderItemEventDTO {
        private final Long productId;
        private final Integer quantity;

        @Builder
        public OrderItemEventDTO(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }



}
