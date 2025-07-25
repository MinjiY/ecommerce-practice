package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;

import java.util.List;

public interface ProductRepository{
    Product findById(Long productId);
    Product save(Product product);
    List<Product> findAllById(List<Long> productIds);
    List<Product> saveAll(List<Product> products);
}
