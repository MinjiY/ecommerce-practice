package kr.hhplus.be.server.controller;


import kr.hhplus.be.server.MockDTO.ResponseGetTop5Products;
import kr.hhplus.be.server.MockDTO.ResponseCreateOrder;
import kr.hhplus.be.server.MockDTO.RequestIssueCoupon;
import kr.hhplus.be.server.MockDTO.RequestCreateOrder;
import kr.hhplus.be.server.MockDTO.ResponseGetPayMoney;
import kr.hhplus.be.server.MockDTO.ResponseChargePayMoney;
import kr.hhplus.be.server.MockDTO.RequestChargePayMoney;
import kr.hhplus.be.server.MockDTO.ResponseIssueCoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MockController implements MockDescription {

    @GetMapping("/pay-money")
    public ResponseEntity<MockApiResponse<ResponseGetPayMoney>> getPayMoney(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(MockApiResponse.success(new ResponseGetPayMoney()));
    }

    @PostMapping("/pay-money")
    public ResponseEntity<MockApiResponse<ResponseChargePayMoney>> chargePayMoney(
            @RequestBody RequestChargePayMoney requestChargePayMoney
    ) {
        return ResponseEntity.ok(MockApiResponse.success(
                new ResponseChargePayMoney()));
    }

    @PostMapping("/coupons")
    public ResponseEntity<MockApiResponse<ResponseIssueCoupon>> issueCoupon(
        @RequestBody RequestIssueCoupon requestIssueCoupon
    ) {
        return ResponseEntity.ok(MockApiResponse.success(
                new ResponseIssueCoupon()));

    }

    @PostMapping("/orders")
    public ResponseEntity<MockApiResponse<ResponseCreateOrder>> createOrder(
            @RequestBody RequestCreateOrder requestCreateOrder
    ) {
        return ResponseEntity.ok(MockApiResponse.success(
                new ResponseCreateOrder()));
    }

    @GetMapping("products/top-5")
    public ResponseEntity<MockApiResponse<ResponseGetTop5Products>> getTop5Products() {
        return ResponseEntity.ok(MockApiResponse.success(
                new ResponseGetTop5Products()));
    }


}
