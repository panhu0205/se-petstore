package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.cart.CartDto;
import com.services.api.form.cart.CreateCartForm;
import com.services.api.form.cart.UpdateCartForm;
import com.services.api.form.cartdetail.AddToCartForm;
import com.services.api.mapper.CartMapper;
import com.services.api.storage.criteria.CartCriteria;
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
@RequestMapping(value = "/v1/cart")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<CartDto>> list(CartCriteria cartCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<CartDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Cart> cartPage = cartRepository.findAll(cartCriteria.getSpecification(), pageable);

        ResponseListObj<CartDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(cartMapper.fromEntityListToDtoList(cartPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(cartPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List carts success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CartDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<CartDto> apiMessageDto = new ApiMessageDto<>();
        var cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get cart");
            return apiMessageDto;
        }
        var cartDto = cartMapper.fromEntityToDto(cart);
        apiMessageDto.setData(cartDto);
        apiMessageDto.setMessage("Get the cart success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCartForm createCartForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cart = new Cart();
        Account customer = accountRepository.findById(createCartForm.getCartCustomerId()).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist");
            return apiMessageDto;
        }
        cart.setCustomer(customer);
        cartRepository.save(cart);
        apiMessageDto.setMessage("Create new cart success");

        return apiMessageDto;
    }

    @PostMapping(value = "/add-to-cart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> addToCart(@Valid @RequestBody AddToCartForm addToCartForm,
            BindingResult bindingResult, @Valid @RequestBody Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto= new ApiMessageDto<>();
        Product product = productRepository.findById(addToCartForm.getCartDetailProductId()).orElse(null);
        if (product == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product not found!");
            return apiMessageDto;
        }
        CartDetail cartDetail = new CartDetail();
        Cart cart = cartRepository.findByCustomerId(id).orElse(null);
        if(cart == null)
        {   
            cart = new Cart();
            Account customer = accountRepository.findById(id).orElse(null);
            cart.setCustomer(customer);
            cartRepository.save(cart);
            cartDetail.setCart(cart);            
            cartDetail.setQuantity(addToCartForm.getCartDetailQuantity());
        }else{ 
            cartDetail = cartDetailRepository.findByProductIdAndCartId(addToCartForm.getCartDetailProductId(), cart.getId()).orElse(null);
            if(cartDetail != null){
                cartDetail.setQuantity(cartDetail.getQuantity() + addToCartForm.getCartDetailQuantity());
            }else
            {
                cartDetail = new CartDetail();
                cartDetail.setProduct(product);
                cartDetail.setQuantity(addToCartForm.getCartDetailQuantity());
                cartDetail.setCart(cart);
            }
        }
        cartDetailRepository.save(cartDetail);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Add to Cart success!");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCartForm updateCartForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cart = cartRepository.findById(updateCartForm.getCartId()).orElse(null);
        if (cart == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cart does not exist to update");
            return apiMessageDto;
        }
        Account customer = accountRepository.findById(updateCartForm.getCartCustomerId()).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist to update");
            return apiMessageDto;
        }
        cart.setCustomer(customer);
        cartRepository.save(cart);
        apiMessageDto.setMessage("Update cart success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cart does not exist to delete");
            return apiMessageDto;
        }
        cartRepository.deleteById(id);
        apiMessageDto.setMessage("Delete cart success");
        return apiMessageDto;
    }

}
