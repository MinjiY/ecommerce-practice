package kr.hhplus.be.server.product.infrastructure.repository.redis;

import kr.hhplus.be.server.config.jpa.RedisKeys;
import kr.hhplus.be.server.product.application.ProductRedisRepository;
import kr.hhplus.be.server.product.application.dto.RankProductDTO;
import kr.hhplus.be.server.product.domain.TopNProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRedisRepositoryImpl implements ProductRedisRepository {

    private final ProductRedisTemplateRepository productRedisTemplateRepository;

    @Override
    public void writeTopNLastMDays(int n){
        List<String> keys = new ArrayList<>();
        for(int i=n;i>=0; i--){
            keys.add(RedisKeys.getRankOrderProductsNDaysAgo(i));
        }
        String rankOrderProducts3DaysKey = RedisKeys.getRankOrderProducts3Days();
        productRedisTemplateRepository.unionAndStore(keys, rankOrderProducts3DaysKey, n);
        productRedisTemplateRepository.expireIfNoTtl(rankOrderProducts3DaysKey, RedisKeys.RANK_ORDER_PRODUCTS_3_DAYS_TTL);
    }

    @Override
    public List<TopNProductId> getTopNLastMDays(int n, int limit) {
        String rankOrderProducts3DaysKey = RedisKeys.getRankOrderProducts3Days();
        List<TopNProductId> rankProducts = productRedisTemplateRepository.getRankProductsTopN(rankOrderProducts3DaysKey, limit);
        return rankProducts;
    }

}
