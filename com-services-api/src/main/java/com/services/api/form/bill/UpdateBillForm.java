package com.services.api.form.bill;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateBillForm {
    @NotNull(message = "billId cannot be null")@ApiModelProperty(name = "billId")
    private Long billId;
    @NotNull(message = "billExportedDate cannot be null")
    @ApiModelProperty(name = "billExportedDate")
    private Date billExportedDate;
    @NotNull(message = "billCustomerId cannot be null")
    @ApiModelProperty(name = "billCustomer")
    private Long billCustomerId;
    @NotNull(message = "billStaffId cannot be null")
    @ApiModelProperty(name = "billStaff")
    private Long billStaffId;
    @NotNull(message = "billSubTotal cannot be null")
    @ApiModelProperty(name = "billSubTotal")
    private Integer billSubTotal;
    @NotNull(message = "billShippingFee cannot be null")
    @ApiModelProperty(name = "billShippingFee")
    private Integer billShippingFee;
    }
