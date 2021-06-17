package com.services.api.dto.cartdetail;

import com.services.api.dto.cart.CartDto;
import com.services.api.dto.product.ProductDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartDetailDto {
    @ApiModelProperty(name = "cartDetailId")
    private Long cartDetailId; 
    @ApiModelProperty(name = "cartDetailBill")
    private CartDto cartDetailCart;
    @ApiModelProperty(name = "cartDetailProduct")
    private ProductDto cartDetailProduct;
    @ApiModelProperty(name = "cartDetailQuantity")
    private Integer cartDetailQuantity;
}
