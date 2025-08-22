package kr.hhplus.be.server.order.application;


import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.point.application.PointService;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.product.application.ProductService;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderFacadeService {

    private final ProductService productService;
    private final PointService pointService;
    //private final PaymentService paymentService;
    private final OrderService orderService;

    public OrderCommandDTO.CreateOrderResult createOrder(OrderCommandDTO.CreateOrderCommand createOrderCommand){
        // 상품 재고 감소
        List<ProductServiceDTO.ProductResult> productResults = productService.decreaseStock(createOrderCommand.getOrderedProducts());

        // 포인트 차감
        PointCommandDTO.WithDrawPointResult withDrawPointResult = pointService.withdrawPoint(PointCommandDTO.withdrawPointCommand.builder()
                        .userId(createOrderCommand.getUserId())
                        .amount(createOrderCommand.getOrderedAmount())
                .build());

        // 결제 내역 기록
        //OrderCommandDTO.PaymentHistoryResult paymentHistoryResult =  paymentService.processPayment(createOrderCommand.toPaymentCommand());
        OrderCommandDTO.CreateOrderResult createOrderResult = orderService.createOrder(createOrderCommand);
        //createOrderResult.setPaymentHistoryId(paymentHistoryResult.getPaymentHistoryId());
        //createOrderResult.setPaymentStatus(paymentHistoryResult.getPaymentStatus());
        return createOrderResult;
    }

}
