package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.infrastructure.repository.ProductRepository;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResult findProduct(Long productId) {
        return productRepository.findById(productId)
                .map(ProductMapper.INSTANCE::entityToDomain)
                .map(ProductResult::from)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }
}
