package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.MockDTO;
import org.springframework.http.ResponseEntity;

public interface MockDescription {

    @Operation(summary = "유저의 결제 금액 조회", description = "userId로 결제 금액을 조회합니다.")
    ResponseEntity<MockApiResponse<MockDTO.ResponseGetPayMoney>> getPayMoney(
            @Parameter(description = "유저 ID", required = true, example = "123") Long userId);

    @Operation(summary = "유저 결제 금액 충전", description = "요청 정보를 받아 결제 금액을 충전합니다. MockAPI 일때에만 defaultValue가 존재합니다.")
    ResponseEntity<MockApiResponse<MockDTO.ResponseChargePayMoney>> chargePayMoney(MockDTO.RequestChargePayMoney requestChargePayMoney);

    @Operation(summary = "쿠폰 발급", description = "요청 정보를 받아 쿠폰을 발급합니다. MockAPI 일때에만 defaultValue가 존재합니다.")
    ResponseEntity<MockApiResponse<MockDTO.ResponseIssueCoupon>> issueCoupon(MockDTO.RequestIssueCoupon requestIssueCoupon);

    @Operation(summary = "주문 생성", description = "요청 정보를 받아 주문을 생성합니다. MockAPI 일때에만 defaultValue가 존재합니다.")
    ResponseEntity<MockApiResponse<MockDTO.ResponseCreateOrder>> createOrder(MockDTO.RequestCreateOrder requestCreateOrder);

    @Operation(summary = "TOP 5 상품 조회", description = "가장 인기 있는 TOP 5 상품을 조회합니다.")
    ResponseEntity<MockApiResponse<MockDTO.ResponseGetTop5Products>> getTop5Products();
}
