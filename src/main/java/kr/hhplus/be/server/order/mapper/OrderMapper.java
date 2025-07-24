package kr.hhplus.be.server.order.mapper;


import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order entityToDomain(OrderEntity orderEntity);

    OrderEntity domainToEntity(Order order);
}
