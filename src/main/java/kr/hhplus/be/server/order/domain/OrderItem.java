package kr.hhplus.be.server.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private Long userId;
    private String productName;
    private Long productAmount;
    private Integer orderQuantity;
    private Long productId;

    public Long getCalculateAmount() {
        return productAmount * orderQuantity;
    }
}
