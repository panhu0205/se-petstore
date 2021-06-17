package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.orderdetail.OrderDetailDto;
import com.services.api.form.orderdetail.CreateOrderDetailForm;
import com.services.api.form.orderdetail.UpdateOrderDetailForm;
import com.services.api.mapper.OrderDetailMapper;
import com.services.api.storage.criteria.OrderDetailCriteria;
import com.services.api.storage.model.Order;
import com.services.api.storage.model.OrderDetail;
import com.services.api.storage.model.Product;
import com.services.api.storage.repository.OrderDetailRepository;
import com.services.api.storage.repository.OrderRepository;
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
@RequestMapping(value = "/v1/orderdetail")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<OrderDetailDto>> list(OrderDetailCriteria orderDetailCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<OrderDetailDto>> apiMessageDto = new ApiMessageDto<>();

        Page<OrderDetail> orderDetailPage = orderDetailRepository.findAll(orderDetailCriteria.getSpecification(), pageable);

        ResponseListObj<OrderDetailDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(orderDetailMapper.fromEntityListToDtoList(orderDetailPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(orderDetailPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List orderDetails success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrderDetailDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<OrderDetailDto> apiMessageDto = new ApiMessageDto<>();
        var orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get orderDetail");
            return apiMessageDto;
        }
        var orderDetailDto = orderDetailMapper.fromEntityToDto(orderDetail);
        apiMessageDto.setData(orderDetailDto);
        apiMessageDto.setMessage("Get the orderDetail success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateOrderDetailForm createOrderDetailForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var orderDetail = orderDetailMapper.fromCreateFormToEntity(createOrderDetailForm);
        Order order = orderRepository.findById(createOrderDetailForm.getOrderDetailOrderId()).orElse(null);
        if (order == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Order does not exist");
            return apiMessageDto;
        }
        orderDetail.setOrder(order);
        Product product = productRepository.findById(createOrderDetailForm.getOrderDetailProductId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist");
            return apiMessageDto;
        }
        orderDetail.setProduct(product);
        orderDetailRepository.save(orderDetail);
        apiMessageDto.setMessage("Create new orderDetail success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateOrderDetailForm updateOrderDetailForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var orderDetail = orderDetailRepository.findById(updateOrderDetailForm.getOrderDetailId()).orElse(null);
        if (orderDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("OrderDetail does not exist to update");
            return apiMessageDto;
        }
        orderDetailMapper.fromUpdateFormToEntity(updateOrderDetailForm, orderDetail);
        Order order = orderRepository.findById(updateOrderDetailForm.getOrderDetailOrderId()).orElse(null);
        if (order == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Order does not exist to update");
            return apiMessageDto;
        }
        orderDetail.setOrder(order);
        Product product = productRepository.findById(updateOrderDetailForm.getOrderDetailProductId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist to update");
            return apiMessageDto;
        }
        orderDetail.setProduct(product);
        orderDetailRepository.save(orderDetail);
        apiMessageDto.setMessage("Update orderDetail success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("OrderDetail does not exist to delete");
            return apiMessageDto;
        }
        orderDetailRepository.deleteById(id);
        apiMessageDto.setMessage("Delete orderDetail success");
        return apiMessageDto;
    }

}
