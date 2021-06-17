package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.cart.CartDto;
import com.services.api.storage.model.Cart;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "id", target = "cartId")
    @Mapping(source = "customer.id", target = "cartCustomer.id")
    CartDto fromEntityToDto(Cart cart);

    @IterableMapping(elementTargetType = CartDto.class)
    List<CartDto> fromEntityListToDtoList(List<Cart> cartList);
}
