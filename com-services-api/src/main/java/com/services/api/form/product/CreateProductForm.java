package com.services.api.form.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateProductForm {

    @NotEmpty(message = "postTitle cannot be empty") 
    @ApiModelProperty(name = "postTitle")
    private String postTitle;
    @NotEmpty(message = "postImage cannot be empty") 
    @ApiModelProperty(name = "postImage")
    private String postImage;
    @NotEmpty(message = "postShortDescription cannot be empty") 
    @ApiModelProperty(name = "postShortDescription")
    private String postShortDescription;
    @NotEmpty(message = "postLongDescription cannot be empty") 
    @ApiModelProperty(name = "postLongDescription")
    private String postLongDescription;
    @NotNull(message = "Product price must not be null")
    private Integer price;
    @NotNull(message = "Product available quantity must not be null")
    private Integer quantity;
    @NotNull(message = "Product status must not be null")
    private Integer status;
    @NotNull(message = "Product weight must not be null")
    private Integer weight;
    @NotEmpty(message = "Product origin must not be empty")
    private String origin;
    @NotNull(message = "productCategoryId cannot be null")
    private String productCategoryName;
}
