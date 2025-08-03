package kr.hhplus.be.server.product.application.dto;

public interface findProductDTO {

    Long getProductId();
    String getName();
    String getDescription();
    String getCategory();
    Long getPrice();
    Integer getQuantity();
    String getProductState();
    Long getTotalSold();

}
