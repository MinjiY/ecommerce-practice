package kr.hhplus.be.server.product.mapper;


import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity domainToEntity(Product product);
    Product entityToDomain(ProductEntity productEntity);

    ProductServiceDTO.ProductResult toProductResponse(Product product);
}
