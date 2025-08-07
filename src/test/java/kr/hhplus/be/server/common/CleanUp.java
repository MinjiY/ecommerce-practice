package kr.hhplus.be.server.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import jakarta.persistence.metamodel.EntityType;

@Component
@RequiredArgsConstructor
@Slf4j
public class CleanUp {
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    public void all() {
        var tables = entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> {
                    // 엔티티 클래스에서 @Table 어노테이션의 실제 테이블명 가져오기
                    String tableName = entityType.getJavaType().getAnnotation(Table.class) != null ?
                            entityType.getJavaType().getAnnotation(Table.class).name() :
                            entityType.getJavaType().getSimpleName().toLowerCase();

                    // 테이블명이 비어있으면 클래스명을 소문자로 변환하여 사용
                    return tableName.isEmpty() ?
                            entityType.getJavaType().getSimpleName().toLowerCase() :
                            tableName;
                })
                .toList();

        try {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

            tables.forEach(table -> {
                try {
                    jdbcTemplate.execute("TRUNCATE TABLE " + table);
                } catch (Exception e) {
                    // 테이블이 존재하지 않는 경우 등의 오류 무시
                    log.warn("테이블 {}에 대한 truncate 실패 (무시됨): {}", table, e.getMessage());
                }
            });
        } finally {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }
}
