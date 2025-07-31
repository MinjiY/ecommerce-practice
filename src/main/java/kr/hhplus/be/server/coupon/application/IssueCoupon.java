package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;

public interface IssueCoupon {

    CouponCommandDTO.issueCouponResult issueCoupon(CouponCommandDTO.issueCouponCommand issueCouponCommand);
}
