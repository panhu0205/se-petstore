package com.services.api.dto.productcategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCategoryDto {
    @ApiModelProperty(name = "productCategoryId")
    private Long productCategoryId; 
    @ApiModelProperty(name = "productCategoryName")
    private String productCategoryName;
    @ApiModelProperty(name = "productCategoryDescription")
    private String productCategoryDescription;
}
