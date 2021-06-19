package com.services.api.dto.order;

import com.services.api.dto.account.AccountDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDto {
    @ApiModelProperty(name = "orderId")
    private Long orderId; 
    @ApiModelProperty(name = "orderState")
    private Integer orderState;
    @ApiModelProperty(name = "orderCustomer")
    private AccountDto orderCustomer;
    @ApiModelProperty(name = "orderAddress")
    private String orderAddress;
    @ApiModelProperty(name = "orderTotal")
    private Long orderTotal;
}
