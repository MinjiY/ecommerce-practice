package kr.hhplus.be.server.product.presentation;

import kr.hhplus.be.server.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScheduler {

    private final ProductService productService;

    //@Scheduled(cron = "0 * * * * *") // 매 분으로 테스트
    @Scheduled(cron="0 59 23 * * *")
    public void updateProductStock() {
        productService.updateProductOrderQuantityRank3Days();
    }
}
