package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;

public interface ProductRepository{
    Product findById(Long productId);
    Product save(ProductEntity productEntity);
}
