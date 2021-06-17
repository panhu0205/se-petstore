package com.services.api.form.cartdetail;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateCartDetailForm {
    @NotNull(message = "cartDetailId cannot be null")
    @ApiModelProperty(name = "cartDetailId")
    private Long cartDetailId; 
    @NotNull(message = "cartDetailBill cannot be null")
    @ApiModelProperty(name = "cartDetailBill")
    private Long cartDetailCartId;
    @NotNull(message = "cartDetailProduct cannot be null")
    @ApiModelProperty(name = "cartDetailProduct")
    private Long cartDetailProductId;
    @NotNull(message = "cartDetailQuantity cannot be null")
    @ApiModelProperty(name = "cartDetailQuantity")
    private Integer cartDetailQuantity;
}
