package com.services.api.form.cart;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateCartForm {
    @NotNull(message = "cartId cannot be null")
    @ApiModelProperty(name = "cartId")
    private Long cartId; 
    @NotNull(message = "cartCustomer cannot be null")
    @ApiModelProperty(name = "cartCustomer")
    private Long cartCustomerId;
}
