package kr.hhplus.be.server.product.infrastructure.repository;

import kr.hhplus.be.server.product.application.dto.findProductDTO;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query(nativeQuery = true, value = QueryString.FIND_TOP_N_PRODUCTS_LAST_M_DAYS)
    List<findProductDTO> findTopNProductsLastMDays(
            @Param("currentDate") LocalDate currentDate,
            @Param("topN") Long topN,
            @Param("lastM") Long lastM
    );

    class QueryString {

        private QueryString() {
            throw new IllegalStateException("Query Class");
        }

        public static final String FIND_TOP_N_PRODUCTS_LAST_M_DAYS = """
                SELECT
                        PRODUCT.PRODUCT_ID,
                        PRODUCT.NAME,
                        PRODUCT.DESCRIPTION,
                        PRODUCT.CATEGORY,
                        PRODUCT.PRICE,
                        PRODUCT.CREATED_AT,
                        PRODUCT.UPDATED_AT,
                        PRODUCT.QUANTITY,
                        PRODUCT.PRODUCT_STATE,
                        TOP5.TOTAL_SOLD
                    FROM PRODUCT
                             JOIN (
                        SELECT
                            PRODUCT_ID,
                            SUM(ORDER_QUANTITY) AS TOTAL_SOLD
                        FROM ORDER_ITEM
                        WHERE ORDER_DATE >= :currentDate - INTERVAL :lastM DAY
                        GROUP BY PRODUCT_ID
                        ORDER BY TOTAL_SOLD DESC
                        LIMIT :topN
                    ) TOP5 ON PRODUCT.PRODUCT_ID = TOP5.PRODUCT_ID;
                """;
    }
}
