package com.services.api.form.order;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateOrderForm {
    @NotNull(message = "cartDetailId cannot be null")
    @ApiModelProperty(name = "orderState")
    private Integer orderState;
    @NotNull(message = "cartDetailId cannot be null")
    @ApiModelProperty(name = "orderCustomerId")
    private Long orderCustomerId;
}
