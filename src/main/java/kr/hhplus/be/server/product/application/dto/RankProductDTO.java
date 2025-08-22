package kr.hhplus.be.server.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RankProductDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RankProductQuantityDTO {
        private Long productId;   // productId
        private Double quantity ;  // 판매수/점수
    }
}
