package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.productcategory.ProductCategoryDto;
import com.services.api.form.productcategory.CreateProductCategoryForm;
import com.services.api.form.productcategory.UpdateProductCategoryForm;
import com.services.api.mapper.ProductCategoryMapper;
import com.services.api.storage.criteria.ProductCategoryCriteria;
import com.services.api.storage.model.ProductCategory;
import com.services.api.storage.repository.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/product-category")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductCategoryDto>> list(ProductCategoryCriteria productCategoryCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<ProductCategoryDto>> apiMessageDto = new ApiMessageDto<>();

        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(productCategoryCriteria.getSpecification(), pageable);

        ResponseListObj<ProductCategoryDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(productCategoryMapper.fromEntityListToDtoList(productCategoryPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(productCategoryPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List productCategorys success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductCategoryDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<ProductCategoryDto> apiMessageDto = new ApiMessageDto<>();
        var productCategory = productCategoryRepository.findById(id).orElse(null);
        if (productCategory == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get productCategory");
            return apiMessageDto;
        }
        var productCategoryDto = productCategoryMapper.fromEntityToDto(productCategory);
        apiMessageDto.setData(productCategoryDto);
        apiMessageDto.setMessage("Get the productCategory success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductCategoryForm createProductCategoryForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var productCategory = productCategoryMapper.fromCreateFormToEntity(createProductCategoryForm);

        productCategoryRepository.save(productCategory);
        apiMessageDto.setMessage("Create new productCategory success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductCategoryForm updateProductCategoryForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var productCategory = productCategoryRepository.findById(updateProductCategoryForm.getProductCategoryId()).orElse(null);
        if (productCategory == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("ProductCategory does not exist to update");
            return apiMessageDto;
        }
        productCategoryMapper.fromUpdateFormToEntity(updateProductCategoryForm, productCategory);

        productCategoryRepository.save(productCategory);
        apiMessageDto.setMessage("Update productCategory success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var productCategory = productCategoryRepository.findById(id).orElse(null);
        if (productCategory == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("ProductCategory does not exist to delete");
            return apiMessageDto;
        }
        productCategoryRepository.deleteById(id);
        apiMessageDto.setMessage("Delete productCategory success");
        return apiMessageDto;
    }

}
