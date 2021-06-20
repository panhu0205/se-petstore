package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.product.ProductDto;
import com.services.api.form.product.CreateProductForm;
import com.services.api.form.product.UpdateProductForm;
import com.services.api.mapper.ProductMapper;
import com.services.api.storage.criteria.ProductCriteria;
import com.services.api.storage.model.Post;
import com.services.api.storage.model.Product;
import com.services.api.storage.repository.PostRepository;
import com.services.api.storage.repository.ProductRepository;

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
@RequestMapping(value = "/v1/product")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<ProductDto>> list(ProductCriteria productCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<ProductDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);

        ResponseListObj<ProductDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(productMapper.fromEntityListToDtoList(productPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(productPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List products success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<ProductDto> apiMessageDto = new ApiMessageDto<>();
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get product");
            return apiMessageDto;
        }
        var productDto = productMapper.fromEntityToDto(product);
        apiMessageDto.setData(productDto);
        apiMessageDto.setMessage("Get the product success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductForm createProductForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var product = productMapper.fromCreateFormToEntity(createProductForm);
        Post post = postRepository.findById(createProductForm.getPostId()).orElse(null);
        if (post == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post is not found");
            return apiMessageDto;
        }
        product.setPost(post);
        productRepository.save(product);
        apiMessageDto.setMessage("Create new product success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductForm updateProductForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var product = productRepository.findById(updateProductForm.getId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist to update");
            return apiMessageDto;
        }
        productMapper.fromUpdateFormToEntity(updateProductForm, product);
        Post post = postRepository.findById(updateProductForm.getPostId()).orElse(null);
        if (post == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post is not found");
            return apiMessageDto;
        }
        product.setPost(post);

        productRepository.save(product);
        apiMessageDto.setMessage("Update product success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist to delete");
            return apiMessageDto;
        }
        productRepository.deleteById(id);
        apiMessageDto.setMessage("Delete product success");
        return apiMessageDto;
    }

}
