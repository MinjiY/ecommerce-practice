package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;

import java.util.List;

public interface ProductService {
    ProductServiceDTO.ProductResult findProduct(Long productId);
    List<ProductServiceDTO.ProductResult> decreaseStock(List<OrderCommandDTO.CreateOrderItemCommand> orderedProducts);
    List<ProductServiceDTO.GetTopN> getTopNBestSellingProductsLastMDays(Long topN, Long lastM);
}
