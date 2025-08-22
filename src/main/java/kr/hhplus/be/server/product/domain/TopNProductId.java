package kr.hhplus.be.server.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopNProductId {
    private Long productId;
    private Double totalSold;
}
