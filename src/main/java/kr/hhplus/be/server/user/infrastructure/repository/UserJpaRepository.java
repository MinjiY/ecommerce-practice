package kr.hhplus.be.server.user.infrastructure.repository;

import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
