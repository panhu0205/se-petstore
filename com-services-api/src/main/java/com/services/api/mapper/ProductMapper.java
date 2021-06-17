package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.product.ProductDto;
import com.services.api.form.product.CreateProductForm;
import com.services.api.form.product.UpdateProductForm;
import com.services.api.storage.model.Product;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "origin", target = "origin")
    ProductDto fromEntityToDto(Product product);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "origin", target = "origin")
    Product fromCreateFormToEntity(CreateProductForm createProductForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "origin", target = "origin")
    void fromUpdateFormToEntity(UpdateProductForm updateProductForm, @MappingTarget Product product);

    @IterableMapping(elementTargetType = ProductDto.class)
    List<ProductDto> fromEntityListToDtoList(List<Product> productList);
}
