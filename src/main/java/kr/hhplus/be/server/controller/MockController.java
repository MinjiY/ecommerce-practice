package kr.hhplus.be.server.controller;


import kr.hhplus.be.server.MockDTO.ResponseGetTop5Products;
import kr.hhplus.be.server.MockDTO.ResponseCreateOrder;
import kr.hhplus.be.server.MockDTO.RequestIssueCoupon;
import kr.hhplus.be.server.MockDTO.RequestCreateOrder;
import kr.hhplus.be.server.MockDTO.ResponseGetPayMoney;
import kr.hhplus.be.server.MockDTO.ResponseChargePayMoney;
import kr.hhplus.be.server.MockDTO.RequestChargePayMoney;
import kr.hhplus.be.server.MockDTO.ResponseGetProduct;
import kr.hhplus.be.server.MockDTO.ResponseIssueCoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MockController implements MockDescription {

    @GetMapping("/pay-money")
    public ResponseEntity<ApiResponse<ResponseGetPayMoney>> getPayMoney(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(ApiResponse.success(new ResponseGetPayMoney()));
    }

    @PostMapping("/pay-money")
    public ResponseEntity<ApiResponse<ResponseChargePayMoney>> chargePayMoney(
            @RequestBody RequestChargePayMoney requestChargePayMoney
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                new ResponseChargePayMoney()));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ResponseGetProduct>> getProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                new ResponseGetProduct()));
    }

    @PostMapping("/coupons")
    public ResponseEntity<ApiResponse<ResponseIssueCoupon>> issueCoupon(
        @RequestBody RequestIssueCoupon requestIssueCoupon
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                new ResponseIssueCoupon()));

    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<ResponseCreateOrder>> createOrder(
            @RequestBody RequestCreateOrder requestCreateOrder
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                new ResponseCreateOrder()));
    }

    @GetMapping("products/top-5")
    public ResponseEntity<ApiResponse<ResponseGetTop5Products>> getTop5Products() {
        return ResponseEntity.ok(ApiResponse.success(
                new ResponseGetTop5Products()));
    }


}
