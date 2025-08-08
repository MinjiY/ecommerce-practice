package kr.hhplus.be.server.point;


import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.point.application.PointService;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.infrastructure.repository.PointHistoryJpaRepository;
import kr.hhplus.be.server.point.infrastructure.repository.PointJpaRepository;
import kr.hhplus.be.server.user.domain.User;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import kr.hhplus.be.server.user.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("integration")
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
public class PointIntegrationTest {

    @Autowired
    private PointService pointService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private CleanUp cleanUp;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PointJpaRepository pointJpaRepository;

    @Autowired
    private PointHistoryJpaRepository pointHistoryJpaRepository;

    private User user;

    private Point point;

    @BeforeEach
    void setUp() {
        Long userId = 123L;
        Long initinalAmount= 1000000L;
        user = User.builder()
                .userId(userId)
                .accounts("abc123@naver.com")
                .build();
//        UserEntity userEntity = userJpaRepository.save(UserEntity.builder()
//                .userId(userId)
//                        .accounts("abc123@naver.com")
//                        .build()
//        );
//        user = User.builder()
//                .userId(userEntity.getUserId())
//                .accounts(userEntity.getAccounts())
//                .build();

        PointEntity pointEntity = pointJpaRepository.save(
                PointEntity.builder()
                        .userId(user.getUserId())
                        .balance(initinalAmount)
                        .build()
        );

        point = Point.builder()
                .pointId(pointEntity.getPointId())
                .balance(pointEntity.getBalance())
                .userId(pointEntity.getUserId())
                .build();
    }
    @AfterEach
    void tearDown(){
        cleanUp.all();
    }

    @DisplayName("포인트 충전 통합 테스트")
    @Test
    public void pointChargeIntegrationTest() {
        // given
        Long chargeAmount = 10000L;
        Long expectedBalance = point.getBalance() + chargeAmount;
        PointCommandDTO.chargePointCommand command = PointCommandDTO.chargePointCommand.builder()
                .userId(user.getUserId())
                .amount(chargeAmount)
                .build();

        // when
        PointCommandDTO.ChargePointResult result = pointService.chargePoint(command);

        // Then
        PointEntity findPoint = pointJpaRepository.findByUserId(
                user.getUserId()
        ).orElseThrow();

        assertThat(result.getBalance()).isEqualTo(expectedBalance);
        assertThat(findPoint.getBalance()).isEqualTo(expectedBalance);
    }


}