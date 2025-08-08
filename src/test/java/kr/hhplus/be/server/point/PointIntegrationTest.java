package kr.hhplus.be.server.point;


import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.point.application.PointRepository;
import kr.hhplus.be.server.point.application.PointService;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.infrastructure.repository.PointHistoryJpaRepository;
import kr.hhplus.be.server.point.infrastructure.repository.PointJpaRepository;
import kr.hhplus.be.server.user.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private PointRepository pointRepository;

    @Autowired
    private PointHistoryJpaRepository pointHistoryJpaRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

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
        PointEntity findPoint = pointJpaRepository.findByUserIdForTest(
                user.getUserId()
        ).orElseThrow();

        assertThat(result.getBalance()).isEqualTo(expectedBalance);
        assertThat(findPoint.getBalance()).isEqualTo(expectedBalance);
    }

    @DisplayName("포인트 사용 통합 테스트")
    @Test
    public void pointWithdrawIntegrationTest() {
        // given
        Long useAmount = 10000L;
        Long expectedBalance = point.getBalance() - useAmount;
        PointCommandDTO.withdrawPointCommand command = PointCommandDTO.withdrawPointCommand.builder()
                .userId(user.getUserId())
                .amount(useAmount)
                .build();
        // when
        PointCommandDTO.WithDrawPointResult result = pointService.withdrawPoint(command);

        // Then
        PointEntity findPoint = pointJpaRepository.findByUserIdForTest(
                user.getUserId()
        ).orElseThrow();

        assertThat(result.getBalance()).isEqualTo(expectedBalance);
        assertThat(findPoint.getBalance()).isEqualTo(expectedBalance);
    }

    @DisplayName("포인트 충전 동시성 테스트 - 동일한 유저가 동시에 포인트 충전 요청을 보냈을 때, 순차적으로 처리된다.")
    @Test
    public void pointChargeConcurrency() throws InterruptedException {
        // given
        Long chargeAmount = 10L;


        int threadCount = 5;
        Long initialPoint = point.getBalance();
        Long expectedChargePoint = initialPoint + (threadCount * chargeAmount);

        runConcurrent(threadCount, () -> pointService.chargePoint(
               PointCommandDTO.chargePointCommand.builder()
                        .userId(user.getUserId())
                        .amount(chargeAmount)
                        .build()
        ));

        // Then
        // findByUserId는 락이 걸려있어 test를 위해 findByUserIdForTest 메서드를 사용
        PointEntity resultEntity = pointJpaRepository.findByUserIdForTest(user.getUserId()).orElseThrow(null);

        assertThat(resultEntity.getBalance()).isEqualTo(expectedChargePoint);
    }

    @DisplayName("포인트 사용 동시성 테스트 - 동일한 유저가 동시에 포인트 사용 요청을 보냈을 때, 순차적으로 처리된다.")
    @Test
    public void pointUseConcurrency() throws InterruptedException {
        // given
        long useAmount = 10L;
        int threadCount = 5;
        Long initialPoint = point.getBalance();
        Long expectedWithdrawPoint = initialPoint - (threadCount * useAmount);

        runConcurrent(threadCount, () -> pointService.withdrawPoint(
                PointCommandDTO.withdrawPointCommand.builder()
                        .userId(user.getUserId())
                        .amount(useAmount)
                        .build())
        );

        // Then
        // findByUserId는 락이 걸려있어 test를 위해 findByUserIdForTest 메서드를 사용
        PointEntity resultEntity = pointJpaRepository.findByUserIdForTest(user.getUserId()).orElseThrow(null);

        assertThat(resultEntity.getBalance()).isEqualTo(expectedWithdrawPoint);
    }

    protected void runConcurrent(int threadCount, Runnable task) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.execute(status -> {
                    try {
                        task.run();
                    } finally {
                        latch.countDown();
                    }
                    return null;
                });
            });
        }

        latch.await();
        executor.shutdown();
    }

}