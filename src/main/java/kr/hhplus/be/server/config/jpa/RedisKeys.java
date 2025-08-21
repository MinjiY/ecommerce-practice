package kr.hhplus.be.server.config.jpa;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RedisKeys {
    private RedisKeys() {}
    // yyyyMMdd
    public static final String RANK_ORDER_PRODUCTS = "rank:products:";

    public static final String RANK_ORDER_PRODUCTS_3_DAYS = "rank:products:3days";


    public static final Duration RANK_ORDER_PRODUCTS_TTL = Duration.ofDays(5);

    public static final Duration RANK_ORDER_PRODUCTS_3_DAYS_TTL = Duration.ofDays(1);


    public static String getRankOrderProductsNDaysAgo(int n) {
        LocalDate date = LocalDate.now(ZoneId.systemDefault()).minusDays(n);
        return String.format(RANK_ORDER_PRODUCTS + "%s", date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    public static String getRankOrderProducts3Days() {
        return String.format(RANK_ORDER_PRODUCTS_3_DAYS);
    }
}
