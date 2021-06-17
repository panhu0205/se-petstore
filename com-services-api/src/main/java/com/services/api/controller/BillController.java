package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.bill.BillDto;
import com.services.api.form.bill.CreateBillForm;
import com.services.api.form.bill.UpdateBillForm;
import com.services.api.mapper.BillMapper;
import com.services.api.storage.criteria.BillCriteria;
import com.services.api.storage.model.Account;
import com.services.api.storage.model.Bill;
import com.services.api.storage.repository.AccountRepository;
import com.services.api.storage.repository.BillRepository;

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
@RequestMapping(value = "/v1/bill")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<BillDto>> list(BillCriteria billCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<BillDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Bill> billPage = billRepository.findAll(billCriteria.getSpecification(), pageable);

        ResponseListObj<BillDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(billMapper.fromEntityListToDtoList(billPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(billPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List bills success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<BillDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<BillDto> apiMessageDto = new ApiMessageDto<>();
        var bill = billRepository.findById(id).orElse(null);
        if (bill == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get bill");
            return apiMessageDto;
        }
        var billDto = billMapper.fromEntityToDto(bill);
        apiMessageDto.setData(billDto);
        apiMessageDto.setMessage("Get the bill success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateBillForm createBillForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var bill = billMapper.fromCreateFormToEntity(createBillForm);
        Account customer = accountRepository.findById(createBillForm.getBillCustomerId()).orElse(null);
        if (customer == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Customer does not exist");
            return apiMessageDto;
        }
        bill.setCustomer(customer);
        Account staff = accountRepository.findById(createBillForm.getBillStaffId()).orElse(null);
        if (staff == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Staff does not exist");
            return apiMessageDto;
        }
        bill.setStaff(staff);
        billRepository.save(bill);
        apiMessageDto.setMessage("Create new bill success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateBillForm updateBillForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Bill bill = billRepository.findById(updateBillForm.getBillId()).orElse(null);
        if (bill == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Bill does not exist to update");
            return apiMessageDto;
        }
        billMapper.fromUpdateFormToEntity(updateBillForm, bill);

        billRepository.save(bill);
        apiMessageDto.setMessage("Update bill success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var bill = billRepository.findById(id).orElse(null);
        if (bill == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Bill does not exist to delete");
            return apiMessageDto;
        }
        billRepository.deleteById(id);
        apiMessageDto.setMessage("Delete bill success");
        return apiMessageDto;
    }

}
