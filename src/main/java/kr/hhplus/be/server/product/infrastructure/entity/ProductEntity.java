package kr.hhplus.be.server.product.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.product.common.ProductState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String description;
    private String category;
    private Long price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Builder
    public ProductEntity(String name, String description, String category, Long price, Integer quantity, ProductState productState) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.productState = productState;
    }

}
