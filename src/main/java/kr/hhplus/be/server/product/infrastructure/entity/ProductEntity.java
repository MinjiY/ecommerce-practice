package kr.hhplus.be.server.product.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.product.common.ProductState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String description;
    private String category;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private ProductState productState;


}
