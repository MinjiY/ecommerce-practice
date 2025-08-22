package kr.hhplus.be.server.order.infrastructure.repository.redis;

import kr.hhplus.be.server.config.jpa.RedisKeys;
import kr.hhplus.be.server.order.application.OrderRedisRepository;
import kr.hhplus.be.server.order.application.dto.OrderEventDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderRedisRepositoryImpl implements OrderRedisRepository {

    private static final String today = LocalDate.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
    private static final String thisWeek = DateTimeFormatter.ofPattern("YYYY-'W'ww")
            .format(LocalDate.now(ZoneId.of("Asia/Seoul")));


    private final OrderRedisTemplateRepository orderRedisTemplateRepository;

    @Override
    public Double addOrderItemQuantityToRankDaily(String productId, Double quantity) {
        return orderRedisTemplateRepository.addToSortedSet(RedisKeys.getRankOrderProductsNDaysAgo(0), productId, quantity);
    }

    @Override
    public void addOrderItemListQuantityToRankDaily(List<OrderEventDTO.OrderItemEventDTO> orderItemEventDTOList) {
        for(OrderEventDTO.OrderItemEventDTO item : orderItemEventDTOList) {
            orderRedisTemplateRepository.addToSortedSet(RedisKeys.getRankOrderProductsNDaysAgo(0), item.getProductId(), item.getQuantity().doubleValue());
        }
        orderRedisTemplateRepository.expireIfNoTtl(RedisKeys.getRankOrderProductsNDaysAgo(0), RedisKeys.RANK_ORDER_PRODUCTS_TTL);
    }

//    public Double addOrderItemQuantityToRankWeekly(String productId, Double quantity) {
//        return orderRedisTemplateRepository.addToSortedSet(RANK_ORDER_PRODUCTS+"weekly:"+thisWeek, productId, quantity);
//    }
}
