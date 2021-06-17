package com.services.api.form.cart;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateCartForm {
    @NotNull(message = "cartCustomer cannot be null")
    @ApiModelProperty(name = "cartCustomer")
    private Long cartCustomerId;
}
