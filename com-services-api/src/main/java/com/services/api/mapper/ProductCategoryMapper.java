package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.productcategory.ProductCategoryDto;
import com.services.api.form.productcategory.CreateProductCategoryForm;
import com.services.api.form.productcategory.UpdateProductCategoryForm;
import com.services.api.storage.model.ProductCategory;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCategoryMapper {

    @Mapping(source = "id", target = "productCategoryId")
    @Mapping(source = "name", target = "productCategoryName")
    @Mapping(source = "description", target = "productCategoryDescription")
    ProductCategoryDto fromEntityToDto(ProductCategory productCategory);

    @Mapping(source = "productCategoryName", target = "name")
    @Mapping(source = "productCategoryDescription", target = "description")
    ProductCategory fromCreateFormToEntity(CreateProductCategoryForm createProductCategoryForm);

    @Mapping(source = "productCategoryId", target = "id")
    @Mapping(source = "productCategoryName", target = "name")
    @Mapping(source = "productCategoryDescription", target = "description")
    void fromUpdateFormToEntity(UpdateProductCategoryForm updateProductCategoryForm, @MappingTarget ProductCategory productCategory);

    @IterableMapping(elementTargetType = ProductCategoryDto.class)
    List<ProductCategoryDto> fromEntityListToDtoList(List<ProductCategory> productCategoryList);
}
