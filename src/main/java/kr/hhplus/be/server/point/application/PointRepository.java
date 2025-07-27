package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.point.domain.Point;

public interface PointRepository {
    Point save(Point point);

}
