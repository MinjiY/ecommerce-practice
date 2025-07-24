package kr.hhplus.be.server.order.mapper;


import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import kr.hhplus.be.server.order.infrastructure.entity.OrderItemEntity;
import kr.hhplus.be.server.order.presentation.dto.RequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order entityToDomain(OrderEntity orderEntity);

    OrderEntity domainToEntity(Order order);

    @Mapping(target= "totalAmount", source = "orderedAmount")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    Order dtoToDomain(OrderCommandDTO.CreateOrderCommand createOrderCommand);

    OrderItem dtoToDomain(OrderCommandDTO.CreateOrderItemCommand createOrderItemCommand);

    OrderEntity domainToOrderEntity(Order order);

    @Mapping(target = "order", ignore = true)
    OrderItemEntity domainToOrderItemEntity(OrderItem orderItem);

    Order entityToOrderDomain(OrderEntity orderEntity);
    OrderItem entityToOrderItemDomain(OrderItemEntity orderItemEntity);

    @Mapping(target = "orderedProducts", ignore = true)
    OrderCommandDTO.CreateOrderCommand requestToCommand(RequestDTO.CreateOrderRequest createOrderRequest);
    OrderCommandDTO.CreateOrderItemCommand requestToCommand(RequestDTO.CreateOrderItemRequest createOrderItemRequest);



}
