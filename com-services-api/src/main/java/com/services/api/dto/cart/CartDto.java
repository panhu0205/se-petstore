package com.services.api.dto.cart;

import com.services.api.dto.account.AccountDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartDto {
    @ApiModelProperty(name = "cartId")
    private Long cartId; 
    @ApiModelProperty(name = "cartCustomer")
    private AccountDto cartCustomer;
}
