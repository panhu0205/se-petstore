package com.services.api.form.productcategory;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateProductCategoryForm {
    @NotNull(message = "productCategoryId cannot be null")
    @ApiModelProperty(name = "productCategoryId")
    private Long productCategoryId; 
    @NotEmpty(message = "productCategoryName cannot be empty") 
    @ApiModelProperty(name = "productCategoryName")
    private String productCategoryName;
    @NotEmpty(message = "productCategoryDescription cannot be empty") 
    @ApiModelProperty(name = "productCategoryDescription")
    private String productCategoryDescription;
}
