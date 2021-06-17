package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.bill.BillDto;
import com.services.api.form.bill.CreateBillForm;
import com.services.api.form.bill.UpdateBillForm;
import com.services.api.storage.model.Bill;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {

    @Mapping(source = "id", target = "billId")
    @Mapping(source = "exportedDate", target = "billExportedDate")
    @Mapping(source = "customer.id", target = "billCustomer.id")
    @Mapping(source = "staff.id", target = "billStaff.id")
    @Mapping(source = "subTotal", target = "billSubTotal")
    @Mapping(source = "shippingFee", target = "billShippingFee")
    BillDto fromEntityToDto(Bill bill);

    @Mapping(source = "billExportedDate", target = "exportedDate")
    @Mapping(source = "billSubTotal", target = "subTotal")
    @Mapping(source = "billShippingFee", target = "shippingFee")
    Bill fromCreateFormToEntity(CreateBillForm createBillForm);

    @Mapping(source = "billId", target = "id")
    @Mapping(source = "billExportedDate", target = "exportedDate")
    @Mapping(source = "billSubTotal", target = "subTotal")
    @Mapping(source = "billShippingFee", target = "shippingFee")
    void fromUpdateFormToEntity(UpdateBillForm updateBillForm, @MappingTarget Bill bill);

    @IterableMapping(elementTargetType = BillDto.class)
    List<BillDto> fromEntityListToDtoList(List<Bill> billList);
}
