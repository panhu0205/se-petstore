package com.services.api.dto.bill;

import java.util.Date;

import com.services.api.dto.account.AccountDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillDto {
    @ApiModelProperty(name = "billId")
    private Long billId;
    @ApiModelProperty(name = "billExportedDate")
    private Date billExportedDate;
    @ApiModelProperty(name = "billCustomer")
    private AccountDto billCustomer;
    @ApiModelProperty(name = "billStaff")
    private AccountDto billStaff;
    @ApiModelProperty(name = "billSubTotal")
    private Integer billSubTotal;
    @ApiModelProperty(name = "billShippingFee")
    private Integer billShippingFee;
    }
