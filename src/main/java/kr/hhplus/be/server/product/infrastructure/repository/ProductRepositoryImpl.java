package kr.hhplus.be.server.product.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.product.application.ProductRepository;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaRepository<ProductEntity, Long> productRepository;

    @Override
    public ProductEntity findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
}
