package kr.hhplus.be.server.product.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.product.application.ProductRepository;
import kr.hhplus.be.server.product.application.dto.findProductDTO;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.TopNProduct;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productRepository;
    private ProductMapper productMapper;

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

    @Override
    public List<TopNProduct> findTopNProductsLastMDays(LocalDate currentDate, Long topN, Long lastM) {
        List<findProductDTO> findProductDTOList = productRepository.findTopNProductsLastMDays(currentDate, topN, lastM);
        return findProductDTOList.stream()
                        .map(ProductMapper.INSTANCE::toTopNProductDto)
                                .toList();
    }
}
