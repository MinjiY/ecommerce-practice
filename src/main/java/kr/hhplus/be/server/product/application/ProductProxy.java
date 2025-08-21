//package kr.hhplus.be.server.product.application;
//
//import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
//import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class ProductProxy implements ProductService {
//
//    private final ProductService productService;
//    private final RedissonClient redissonClient;
//
//    private final String DECREASE_PRODUCT_PREFIX = "product:decrease:";
//
//    public ProductProxy(@Qualifier("ProductServiceImpl") ProductService productService,
//                        RedissonClient redissonClient) {
//        this.productService = productService;
//        this.redissonClient = redissonClient;
//    }
//
//    @Override
//    public ProductServiceDTO.ProductResult findProduct(Long productId){
//        return productService.findProduct(productId);
//    }
//
//    @Override
//    public List<ProductServiceDTO.GetTopN> getTopNBestSellingProductsLastMDays(Long topN, Long lastM) {
//        return productService.getTopNBestSellingProductsLastMDays(topN, lastM);
//    }
//
//    @Override
//    public List<ProductServiceDTO.ProductResult> decreaseStock(List<OrderCommandDTO.CreateOrderItemCommand> orderedProducts) {
//        RLock decreaseStockLock = getLockForProduct(DECREASE_PRODUCT_PREFIX, orderedProducts.get(0).getProductId());
//        try {
//            if(!decreaseStockLock.tryLock(500, 500, TimeUnit.MICROSECONDS)) {
//                throw new IllegalStateException("포인트 충전이 이미 진행 중입니다.");
//            }
//            return productService.decreaseStock(orderedProducts);
//        }catch (InterruptedException e) {
//            throw new RuntimeException("충전 실패");
//        } finally {
//            decreaseStockLock.unlock();
//        }
//    }
//
//    private RLock getLockForProduct(String key, Long userId) {
//        String lockKey = key + userId;
//        return redissonClient.getLock(lockKey);
//    }
//}
