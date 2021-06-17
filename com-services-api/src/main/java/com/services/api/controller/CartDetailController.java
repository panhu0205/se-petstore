package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.cartdetail.CartDetailDto;
import com.services.api.form.cartdetail.AddToCartForm;
import com.services.api.form.cartdetail.CreateCartDetailForm;
import com.services.api.form.cartdetail.UpdateCartDetailForm;
import com.services.api.mapper.CartDetailMapper;
import com.services.api.storage.criteria.CartDetailCriteria;
import com.services.api.storage.model.Account;
import com.services.api.storage.model.Cart;
import com.services.api.storage.model.CartDetail;
import com.services.api.storage.model.Product;
import com.services.api.storage.repository.AccountRepository;
import com.services.api.storage.repository.CartDetailRepository;
import com.services.api.storage.repository.CartRepository;
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
@RequestMapping(value = "/v1/cartdetail")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CartDetailController {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<CartDetailDto>> list(CartDetailCriteria cartDetailCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<CartDetailDto>> apiMessageDto = new ApiMessageDto<>();

        Page<CartDetail> cartDetailPage = cartDetailRepository.findAll(cartDetailCriteria.getSpecification(), pageable);

        ResponseListObj<CartDetailDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(cartDetailMapper.fromEntityListToDtoList(cartDetailPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(cartDetailPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List cartDetails success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CartDetailDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<CartDetailDto> apiMessageDto = new ApiMessageDto<>();
        var cartDetail = cartDetailRepository.findById(id).orElse(null);
        if (cartDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get cartDetail");
            return apiMessageDto;
        }
        var cartDetailDto = cartDetailMapper.fromEntityToDto(cartDetail);
        apiMessageDto.setData(cartDetailDto);
        apiMessageDto.setMessage("Get the cartDetail success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCartDetailForm createCartDetailForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cartDetail = cartDetailMapper.fromCreateFormToEntity(createCartDetailForm);
        Cart cart = cartRepository.findById(createCartDetailForm.getCartDetailCartId()).orElse(null);
        if (cart == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cart does not exist");
            return apiMessageDto;
        }
        cartDetail.setCart(cart);
        Product product = productRepository.findById(createCartDetailForm.getCartDetailProductId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist");
            return apiMessageDto;
        }
        cartDetail.setProduct(product);
        cartDetailRepository.save(cartDetail);
        apiMessageDto.setMessage("Create new cartDetail success");

        return apiMessageDto;
    }

    @PostMapping(value = "/add-to-cart/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> addToCart(@Valid @RequestBody AddToCartForm addToCartForm,@Valid @RequestBody Long customerId, BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        CartDetail cartDetail = new CartDetail();
        Account customer = accountRepository.findById(customerId).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist");
            return apiMessageDto;
        }
        Cart cart = cartRepository.findByCustomer(customer).orElse(null);
        if (cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }
        cartDetail.setCart(cart);
        Product product = productRepository.findById(addToCartForm.getCartDetailProductId()).orElse(null);
        if (product == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setData("Product does not exist!");
            return apiMessageDto;
        }else if (product.getQuantity() == 0 || product.getStatus() == 0){
            apiMessageDto.setResult(false);
            apiMessageDto.setData("Product is unavailable!");
            return apiMessageDto;
        }else{
            cartDetail.setProduct(product);
            cartDetailRepository.save(cartDetail);
        }
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Create new cart success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCartDetailForm updateCartDetailForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cartDetail = cartDetailRepository.findById(updateCartDetailForm.getCartDetailId()).orElse(null);
        if (cartDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("CartDetail does not exist to update");
            return apiMessageDto;
        }
        cartDetailMapper.fromUpdateFormToEntity(updateCartDetailForm, cartDetail);
        Cart cart = cartRepository.findById(updateCartDetailForm.getCartDetailCartId()).orElse(null);
        if (cart == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cart does not exist to update");
            return apiMessageDto;
        }
        cartDetail.setCart(cart);
        Product product = productRepository.findById(updateCartDetailForm.getCartDetailProductId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist to update");
            return apiMessageDto;
        }
        cartDetail.setProduct(product);
        cartDetailRepository.save(cartDetail);
        apiMessageDto.setMessage("Update cartDetail success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cartDetail = cartDetailRepository.findById(id).orElse(null);
        if (cartDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("CartDetail does not exist to delete");
            return apiMessageDto;
        }
        cartDetailRepository.deleteById(id);
        apiMessageDto.setMessage("Delete cartDetail success");
        return apiMessageDto;
    }

}
