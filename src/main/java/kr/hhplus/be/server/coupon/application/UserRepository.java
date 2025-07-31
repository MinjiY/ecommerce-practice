package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.user.domain.User;

public interface UserRepository {
    User findByUserId(User user);
}
