package kr.hhplus.be.server.product.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.product.application.ProductRepository;
import kr.hhplus.be.server.product.application.dto.findProductDTO;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.TopNProduct;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import kr.hhplus.be.server.product.mapper.ProductNativeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productRepository;
    private final ProductNativeMapper productNativeMapper;


    @Transactional(readOnly = true)
    @Override
    public Product findById(Long productId) {
        return ProductMapper.INSTANCE.entityToDomain(productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new));
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return ProductMapper.INSTANCE.entityToDomain(productRepository.save(ProductMapper.INSTANCE.domainToEntity(product)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAllById(List<Long> productIds){
        List<Product> products = productRepository.findAllById(productIds).stream()
                .map(ProductMapper.INSTANCE::entityToDomain)
                .toList();
        return products;
    }

    @Transactional
    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> productEntities = products.stream()
                .map(product -> productNativeMapper.domainToEntity(product))
                .toList();
        return productRepository.saveAll(productEntities).stream()
                .map(ProductMapper.INSTANCE::entityToDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TopNProduct> findTopNProductsLastMDays(LocalDate currentDate, Long topN, Long lastM) {
        List<findProductDTO> findProductDTOList = productRepository.findTopNProductsLastMDays(currentDate, topN, lastM);
        return findProductDTOList.stream()
                        .map(ProductMapper.INSTANCE::toTopNProductDto)
                                .toList();
    }
}
