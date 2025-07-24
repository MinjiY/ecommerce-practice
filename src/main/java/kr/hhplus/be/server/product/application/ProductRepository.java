package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;

public interface ProductRepository{
    ProductEntity findById(Long productId);
    ProductEntity save(ProductEntity productEntity);
}
