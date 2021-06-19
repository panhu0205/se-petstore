package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.order.OrderDto;
import com.services.api.form.order.CreateOrderForm;
import com.services.api.form.order.UpdateOrderForm;
import com.services.api.storage.model.Order;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "state", target = "orderState")
    @Mapping(source = "customer.id", target = "orderCustomer.id")
    @Mapping(source = "address", target = "orderAddress")
    @Mapping(source = "total", target = "orderTotal")
    OrderDto fromEntityToDto(Order order);

    @Mapping(source = "orderState", target = "state")
    @Mapping(source = "orderAddress", target = "address")
    Order fromCreateFormToEntity(CreateOrderForm createOrderForm);

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "orderState", target = "state")
    @Mapping(source = "orderAddress", target = "address")
    void fromUpdateFormToEntity(UpdateOrderForm updateOrderForm, @MappingTarget Order order);

    @IterableMapping(elementTargetType = OrderDto.class)
    List<OrderDto> fromEntityListToDtoList(List<Order> orderList);
}
