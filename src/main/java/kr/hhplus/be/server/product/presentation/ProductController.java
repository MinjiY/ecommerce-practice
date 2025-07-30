package kr.hhplus.be.server.product.presentation;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.product.application.ProductService;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 정보 조회", description = "productId로 상품 정보를 조회합니다.")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductServiceDTO.ProductResult>> getProduct(
            @PathVariable Long productId
    ) {
        ProductServiceDTO.ProductResult productResult = productService.findProduct(productId);
        return ResponseEntity.ok(ApiResponse.success(productResult));
    }

}
