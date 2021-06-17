package com.services.api.dto.billdetail;

import com.services.api.dto.bill.BillDto;
import com.services.api.dto.product.ProductDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillDetailDto {
    @ApiModelProperty(name = "billDetailId")
    private Long billDetailId; 
    @ApiModelProperty(name = "billDetailBill")
    private BillDto billDetailBill;
    @ApiModelProperty(name = "billDetailProduct")
    private ProductDto billDetailProduct;
    @ApiModelProperty(name = "billDetailQuantity")
    private Integer billDetailQuantity;
}
