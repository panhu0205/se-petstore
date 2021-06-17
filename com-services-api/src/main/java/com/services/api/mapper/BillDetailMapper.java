package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.billdetail.BillDetailDto;
import com.services.api.form.billdetail.CreateBillDetailForm;
import com.services.api.form.billdetail.UpdateBillDetailForm;
import com.services.api.storage.model.BillDetail;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillDetailMapper {

    @Mapping(source = "id", target = "billDetailId")
    @Mapping(source = "bill.id", target = "billDetailBill.billId")
    @Mapping(source = "product.id", target = "billDetailProduct.id")
    @Mapping(source = "quantity", target = "billDetailQuantity")
    BillDetailDto fromEntityToDto(BillDetail billDetail);

    @Mapping(source = "billDetailQuantity", target = "quantity")
    BillDetail fromCreateFormToEntity(CreateBillDetailForm createBillDetailForm);

    @Mapping(source = "billDetailId", target = "id")
    @Mapping(source = "billDetailQuantity", target = "quantity")
    void fromUpdateFormToEntity(UpdateBillDetailForm updateBillDetailForm, @MappingTarget BillDetail billDetail);

    @IterableMapping(elementTargetType = BillDetailDto.class)
    List<BillDetailDto> fromEntityListToDtoList(List<BillDetail> billDetailList);
}
