package com.services.api.form.orderdetail;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateOrderDetailForm {
    @NotNull(message = "orderDetailId cannot be null")
    @ApiModelProperty(name = "orderDetailId")
    private Long orderDetailId; 
    @NotNull(message = "orderDetailOrderId cannot be null")
    @ApiModelProperty(name = "orderDetailBill")
    private Long orderDetailOrderId;
    @NotNull(message = "orderDetailProductId cannot be null")
    @ApiModelProperty(name = "orderDetailProduct")
    private Long orderDetailProductId;
    @NotNull(message = "orderDetailQuantity cannot be null")
    @ApiModelProperty(name = "orderDetailQuantity")
    private Integer orderDetailQuantity;
    @NotNull(message = "orderDetailPrice cannot be null")
    @ApiModelProperty(name = "orderDetailPrice")
    private Integer orderDetailPrice; 
}
