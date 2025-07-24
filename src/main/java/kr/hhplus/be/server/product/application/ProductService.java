package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;


    public ProductResult findProduct(Long productId) {
        return ProductResult.from(productRepository.findById(productId));
    }
}
