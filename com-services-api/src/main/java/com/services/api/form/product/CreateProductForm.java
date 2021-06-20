package com.services.api.form.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateProductForm {

    @NotNull(message = "postId cannot be null")
    @ApiModelProperty(name = "postId")
    private Long postId;
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
    private Long productCategoryId;
}
