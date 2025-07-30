package kr.hhplus.be.server.order.presentation;


import jakarta.validation.Valid;
import kr.hhplus.be.server.order.application.OrderFacadeService;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.presentation.dto.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private OrderFacadeService orderFacadeService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderCommandDTO.CreateOrderResult>> createOrder(
            @Valid @RequestBody RequestDTO.CreateOrderRequest createOrderRequest
    ) {
        OrderCommandDTO.CreateOrderResult createOrderResult =  orderFacadeService.createOrder(createOrderRequest.toCommand());
        return ResponseEntity.ok(ApiResponse.success(createOrderResult));
    }

}
