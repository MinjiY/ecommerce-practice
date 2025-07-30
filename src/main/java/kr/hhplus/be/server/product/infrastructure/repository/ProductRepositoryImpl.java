package kr.hhplus.be.server.product.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.product.application.ProductRepository;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaRepository<ProductEntity, Long> productRepository;
    private final ProductMapper productMapper;
    @Override
    public Product findById(Long productId) {
        return ProductMapper.INSTANCE.entityToDomain(productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.INSTANCE.entityToDomain(productRepository.save(productMapper.domainToEntity(product)));
    }

    @Override
    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllById(productIds).stream()
                .map(productMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> productEntities = products.stream()
                .map(productMapper::domainToEntity)
                .toList();
        return productRepository.saveAll(productEntities).stream()
                .map(productMapper::entityToDomain)
                .toList();
    }
}
