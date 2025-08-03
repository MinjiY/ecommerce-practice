package kr.hhplus.be.server.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private Long userId;
    private String productName;
    private Long productAmount;
    private Integer orderQuantity;
    private Long productId;

    private LocalDate orderDate;
    private LocalDate updateDate;
    private LocalDateTime orderedAt;
    private LocalDateTime updatedAt;

    public Long getCalculateAmount() {
        return productAmount * orderQuantity;
    }
}
