package kr.hhplus.be.server.coupon.presentation;

import kr.hhplus.be.server.coupon.application.CouponService;
import kr.hhplus.be.server.coupon.application.IssueCouponService;
import kr.hhplus.be.server.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final IssueCouponService IssueCouponService;

    //@Scheduled(cron = "*/10 * * * * *") // 매 분으로 테스트
    @Scheduled(cron = "0 * * * * *") //
    public void popWaitingList() {
        IssueCouponService.issuedWaitingList();
    }
}
