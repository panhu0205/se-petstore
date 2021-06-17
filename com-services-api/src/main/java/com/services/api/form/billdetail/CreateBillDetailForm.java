package com.services.api.form.billdetail;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateBillDetailForm {
    @NotNull(message = "billDetailBill cannot be null")
    @ApiModelProperty(name = "billDetailBill")
    private Long billDetailBillId;
    @NotNull(message = "billDetailProduct cannot be null")
    @ApiModelProperty(name = "billDetailProduct")
    private Long billDetailProductId;
    @NotNull(message = "billDetailQuantity cannot be null")
    @ApiModelProperty(name = "billDetailQuantity")
    private Integer billDetailQuantity;
    }
