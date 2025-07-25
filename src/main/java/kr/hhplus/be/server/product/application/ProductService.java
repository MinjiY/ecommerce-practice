package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;


    public ProductResult findProduct(Long productId) {
        return ProductResult.from(productRepository.findById(productId));
    }

    public List<ProductResult> decreaseStock(List<OrderCommandDTO.CreateOrderItemCommand> orderedProducts) {
        Map<Long, Integer> orderedMap = orderedProducts.stream()
                .collect(Collectors.toMap(
                        OrderCommandDTO.CreateOrderItemCommand::getProductId,
                        OrderCommandDTO.CreateOrderItemCommand::getOrderQuantity
                ));

        List<Product> products = productRepository.findAllById(orderedProducts.stream()
                .map(OrderCommandDTO.CreateOrderItemCommand::getProductId)
                .toList());
        if(products.size() != orderedProducts.size()) {
            throw new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND);
        }
        List<Product> decreasedProducts = products.stream()
                .map(product -> {
                    int orderedQuantity = orderedMap.getOrDefault(product.getProductId(), 0);
                    return product.decreaseQuantity(orderedQuantity);
                })
                .toList();

        return ProductServiceDTO.ProductListResult.from(productRepository.saveAll(decreasedProducts));
    }


}
