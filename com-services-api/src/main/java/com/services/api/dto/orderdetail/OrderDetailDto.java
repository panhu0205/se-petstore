package com.services.api.dto.orderdetail;

import com.services.api.dto.order.OrderDto;
import com.services.api.dto.product.ProductDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDetailDto {
    @ApiModelProperty(name = "orderDetailId")
    private Long orderDetailId; 
    @ApiModelProperty(name = "orderDetailOrder")
    private OrderDto orderDetailOrder;
    @ApiModelProperty(name = "orderDetailProduct")
    private ProductDto orderDetailProduct;
    @ApiModelProperty(name = "orderDetailQuantity")
    private Integer orderDetailQuantity;
    @ApiModelProperty(name = "orderDetailPrice")
    private Integer orderDetailPrice; 
}
