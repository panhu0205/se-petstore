package com.services.api.dto.product;

import com.services.api.dto.post.PostDto;
import com.services.api.dto.productcategory.ProductCategoryDto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private PostDto post;

    private Integer price;

    private Integer quantity;

    private Integer status;

    private Integer weight;

    private String origin;

    private String postTitle;

    private ProductCategoryDto productCategory;
}
