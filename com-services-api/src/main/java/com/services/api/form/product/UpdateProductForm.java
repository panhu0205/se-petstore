package com.services.api.form.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateProductForm {

    @NotNull(message = "Product id must not be null")
    private Long id;
    @NotEmpty(message = "Product name must not be empty")
    private String name;
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
    @NotNull(message = "petPostId cannot be null")
    @ApiModelProperty(name = "petPostId")
    private Long postId;
}
