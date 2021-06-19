package com.services.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.order.OrderDto;
import com.services.api.form.order.CheckOutForm;
import com.services.api.form.order.CreateOrderForm;
import com.services.api.form.order.UpdateOrderForm;
import com.services.api.mapper.OrderMapper;
import com.services.api.storage.criteria.OrderCriteria;
import com.services.api.storage.model.Account;
import com.services.api.storage.model.CartDetail;
import com.services.api.storage.model.Order;
import com.services.api.storage.model.OrderDetail;
import com.services.api.storage.repository.AccountRepository;
import com.services.api.storage.repository.CartDetailRepository;
import com.services.api.storage.repository.OrderDetailRepository;
import com.services.api.storage.repository.OrderRepository;

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
@RequestMapping(value = "/v1/order")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<OrderDto>> list(OrderCriteria orderCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<OrderDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Order> orderPage = orderRepository.findAll(orderCriteria.getSpecification(), pageable);

        ResponseListObj<OrderDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(orderMapper.fromEntityListToDtoList(orderPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(orderPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List orders success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrderDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<OrderDto> apiMessageDto = new ApiMessageDto<>();
        var order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get order");
            return apiMessageDto;
        }
        var orderDto = orderMapper.fromEntityToDto(order);
        apiMessageDto.setData(orderDto);
        apiMessageDto.setMessage("Get the order success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateOrderForm createOrderForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var order = orderMapper.fromCreateFormToEntity(createOrderForm);
        Account customer = accountRepository.findById(createOrderForm.getOrderCustomerId()).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist");
            return apiMessageDto;
        }
        order.setCustomer(customer);
        orderRepository.save(order);
        apiMessageDto.setMessage("Create new order success");

        return apiMessageDto;
    }

    @PostMapping(value = "/checkout/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> checkout(@Valid @RequestBody CheckOutForm checkOutForm, BindingResult bindingResul, @Valid @RequestBody Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Order order = new Order();
        order.setState(0);
        order.setCustomer(accountRepository.findById(id).orElse(null));
        order.setTotal(0L);
        orderRepository.save(order);
        for (Long cartDetailId: checkOutForm.getCartDetailId()){
            CartDetail cartDetail= cartDetailRepository.findById(cartDetailId).orElse(null);
            if (cartDetail == null ){
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Method Unavailable");
                return apiMessageDto;
            }else{
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cartDetail.getQuantity());
                orderDetail.setProduct(cartDetail.getProduct());
                orderDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
                order.setTotal(order.getTotal() + orderDetail.getPrice());
            }
        }
        orderRepository.save(order);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Checkout in progress");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateOrderForm updateOrderForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var order = orderRepository.findById(updateOrderForm.getOrderId()).orElse(null);
        if (order == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Order does not exist to update");
            return apiMessageDto;
        }
        orderMapper.fromUpdateFormToEntity(updateOrderForm, order);
        Account customer = accountRepository.findById(updateOrderForm.getOrderCustomerId()).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist to update");
            return apiMessageDto;
        }
        order.setCustomer(customer);
        orderRepository.save(order);
        apiMessageDto.setMessage("Update order success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Order does not exist to delete");
            return apiMessageDto;
        }
        orderRepository.deleteById(id);
        apiMessageDto.setMessage("Delete order success");
        return apiMessageDto;
    }

}
