package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.cartdetail.CartDetailDto;
import com.services.api.form.cartdetail.CreateCartDetailForm;
import com.services.api.form.cartdetail.UpdateCartDetailForm;
import com.services.api.storage.model.CartDetail;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartDetailMapper {

    @Mapping(source = "id", target = "cartDetailId")
    @Mapping(source = "cart.id", target = "cartDetailCart.cartId")
    @Mapping(source = "product.id", target = "cartDetailProduct.id")
    @Mapping(source = "quantity", target = "cartDetailQuantity")
    CartDetailDto fromEntityToDto(CartDetail cartDetail);

    @Mapping(source = "cartDetailQuantity", target = "quantity")
    CartDetail fromCreateFormToEntity(CreateCartDetailForm createCartDetailForm);

    @Mapping(source = "cartDetailId", target = "id")
    @Mapping(source = "cartDetailQuantity", target = "quantity")
    void fromUpdateFormToEntity(UpdateCartDetailForm updateCartDetailForm, @MappingTarget CartDetail cartDetail);

    @IterableMapping(elementTargetType = CartDetailDto.class)
    List<CartDetailDto> fromEntityListToDtoList(List<CartDetail> cartDetailList);
}
