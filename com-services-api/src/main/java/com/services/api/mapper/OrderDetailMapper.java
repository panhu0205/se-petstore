package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.orderdetail.OrderDetailDto;
import com.services.api.form.orderdetail.CreateOrderDetailForm;
import com.services.api.form.orderdetail.UpdateOrderDetailForm;
import com.services.api.storage.model.OrderDetail;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailMapper {

    @Mapping(source = "id", target = "orderDetailId")
    @Mapping(source = "quantity", target = "orderDetailQuantity")
    @Mapping(source = "price", target = "orderDetailPrice")
    @Mapping(source = "order.id", target = "orderDetailOrder.orderId")
    @Mapping(source = "product.id", target = "orderDetailProduct.id")
    OrderDetailDto fromEntityToDto(OrderDetail orderDetail);

    @Mapping(source = "orderDetailQuantity", target = "quantity")
    @Mapping(source = "orderDetailPrice", target = "price")
    OrderDetail fromCreateFormToEntity(CreateOrderDetailForm createOrderDetailForm);

    @Mapping(source = "orderDetailId", target = "id")
    @Mapping(source = "orderDetailQuantity", target = "quantity")
    @Mapping(source = "orderDetailPrice", target = "price")
    void fromUpdateFormToEntity(UpdateOrderDetailForm updateOrderDetailForm, @MappingTarget OrderDetail orderDetail);

    @IterableMapping(elementTargetType = OrderDetailDto.class)
    List<OrderDetailDto> fromEntityListToDtoList(List<OrderDetail> orderDetailList);
}
