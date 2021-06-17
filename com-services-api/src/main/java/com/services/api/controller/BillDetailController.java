package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.billdetail.BillDetailDto;
import com.services.api.form.billdetail.CreateBillDetailForm;
import com.services.api.form.billdetail.UpdateBillDetailForm;
import com.services.api.mapper.BillDetailMapper;
import com.services.api.storage.criteria.BillDetailCriteria;
import com.services.api.storage.model.Bill;
import com.services.api.storage.model.BillDetail;
import com.services.api.storage.model.Product;
import com.services.api.storage.repository.BillDetailRepository;
import com.services.api.storage.repository.BillRepository;
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
@RequestMapping(value = "/v1/billdetail")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class BillDetailController {

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private BillDetailMapper billDetailMapper;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BillRepository billRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<BillDetailDto>> list(BillDetailCriteria billDetailCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<BillDetailDto>> apiMessageDto = new ApiMessageDto<>();

        Page<BillDetail> billDetailPage = billDetailRepository.findAll(billDetailCriteria.getSpecification(), pageable);

        ResponseListObj<BillDetailDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(billDetailMapper.fromEntityListToDtoList(billDetailPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(billDetailPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List billDetails success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<BillDetailDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<BillDetailDto> apiMessageDto = new ApiMessageDto<>();
        var billDetail = billDetailRepository.findById(id).orElse(null);
        if (billDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get billDetail");
            return apiMessageDto;
        }
        var billDetailDto = billDetailMapper.fromEntityToDto(billDetail);
        apiMessageDto.setData(billDetailDto);
        apiMessageDto.setMessage("Get the billDetail success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateBillDetailForm createBillDetailForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var billDetail = billDetailMapper.fromCreateFormToEntity(createBillDetailForm);
        Bill bill = billRepository.findById(createBillDetailForm.getBillDetailBillId()).orElse(null);
        if (bill == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Bill does not exist");
            return apiMessageDto;
        }
        billDetail.setBill(bill);
        Product product = productRepository.findById(createBillDetailForm.getBillDetailProductId()).orElse(null);
        if (product == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Product does not exist");
            return apiMessageDto;
        }
        billDetail.setProduct(product);
        billDetailRepository.save(billDetail);
        apiMessageDto.setMessage("Create new billDetail success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateBillDetailForm updateBillDetailForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var billDetail = billDetailRepository.findById(updateBillDetailForm.getBillDetailId()).orElse(null);
        if (billDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("BillDetail does not exist to update");
            return apiMessageDto;
        }
        billDetailMapper.fromUpdateFormToEntity(updateBillDetailForm, billDetail);

        billDetailRepository.save(billDetail);
        apiMessageDto.setMessage("Update billDetail success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var billDetail = billDetailRepository.findById(id).orElse(null);
        if (billDetail == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("BillDetail does not exist to delete");
            return apiMessageDto;
        }
        billDetailRepository.deleteById(id);
        apiMessageDto.setMessage("Delete billDetail success");
        return apiMessageDto;
    }

}
