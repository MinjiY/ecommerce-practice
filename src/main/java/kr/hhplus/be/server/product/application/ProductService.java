package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.domain.TopNProduct;
import kr.hhplus.be.server.product.domain.TopNProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRedisRepository productRedisRepository;


    public ProductResult findProduct(Long productId) {
        return ProductResult.from(productRepository.findById(productId));
    }

    @Transactional
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

    /**
     * 최근 M일 동안 가장 많이 팔린 상품을 조회합니다.
     * 요구사항 맞게 parameter 넣어서 재사용하기
     * @param topN 조회할 상품의 개수
     * @param lastM 최근 M일
     * @return 가장 많이 팔린 상품 목록
     */
    @Transactional(readOnly = true)
    public List<ProductServiceDTO.GetTopN> getTopNBestSellingProductsLastMDays(Long topN, Long lastM) {
        List<TopNProduct> products = productRepository.findTopNProductsLastMDays(LocalDate.now(), topN, lastM);
        return ProductServiceDTO.GetTopN.from(products);
    }

    public List<ProductServiceDTO.GetTopN> getTopNBestSellingProductsLastMDaysRedis(Long topN, Long lastM) {
        // Redis에서 최근 M일 동안 가장 많이 팔린 상품을 조회
        List<TopNProductId> productIds =  productRedisRepository.getTopNLastMDays(lastM.intValue(), topN.intValue());

        List<Product> products = productRepository.findAllById(
                productIds.stream()
                        .map(TopNProductId::getProductId)
                        .toList()
        );
        return ProductServiceDTO.GetTopN.of(products);
    }

    public void updateProductOrderQuantityRank3Days() {
        log.info("Updating product order quantity rank for the last 3 days");
        productRedisRepository.writeTopNLastMDays(3);
    }
}
