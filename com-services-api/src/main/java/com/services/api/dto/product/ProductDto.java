package com.services.api.dto.product;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    private Integer status;

    private Integer weight;

    private String origin;

    private String postTitle;

}
