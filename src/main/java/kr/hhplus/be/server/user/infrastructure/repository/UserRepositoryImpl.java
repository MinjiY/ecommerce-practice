package kr.hhplus.be.server.user.infrastructure.repository;

import kr.hhplus.be.server.coupon.application.UserRepository;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.user.domain.User;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private JpaRepository<UserEntity, Long> userJpaRepository;

    @Override
    public User findByUserId(User user) {
        UserEntity userEntity = userJpaRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다. id: " + user.getUserId()));
        return User.builder()
                .userId(userEntity.getUserId())
                .accounts(userEntity.getAccounts())
                .build();

    }
}
