package kr.hhplus.be.server.point.domain;

import lombok.Builder;
import lombok.Getter;


@Getter
public class Point {
    private Long pointId;
    private Long balance;
    private Long userId;

    @Builder
    public Point(Long pointId, Long balance, Long userId) {
        this.pointId = pointId;
        this.balance = balance;
        this.userId = userId;
    }
}