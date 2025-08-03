package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.point.domain.PointHistory;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);

}
