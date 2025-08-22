package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.application.dto.RankProductDTO;
import kr.hhplus.be.server.product.domain.TopNProductId;

import java.util.List;

public interface ProductRedisRepository {
    void writeTopNLastMDays(int n);
    List<TopNProductId> getTopNLastMDays(int n, int limit);
}
