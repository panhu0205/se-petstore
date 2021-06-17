package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.breed.BreedDto;
import com.services.api.form.breed.CreateBreedForm;
import com.services.api.form.breed.UpdateBreedForm;
import com.services.api.mapper.BreedMapper;
import com.services.api.storage.criteria.BreedCriteria;
import com.services.api.storage.model.Breed;
import com.services.api.storage.repository.BreedRepository;

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
@RequestMapping(value = "/v1/breed")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class BreedController {

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private BreedMapper breedMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<BreedDto>> list(BreedCriteria breedCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<BreedDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Breed> breedPage = breedRepository.findAll(breedCriteria.getSpecification(), pageable);

        ResponseListObj<BreedDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(breedMapper.fromEntityListToDtoList(breedPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(breedPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List breeds success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<BreedDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<BreedDto> apiMessageDto = new ApiMessageDto<>();
        var breed = breedRepository.findById(id).orElse(null);
        if (breed == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get breed");
            return apiMessageDto;
        }
        var breedDto = breedMapper.fromEntityToDto(breed);
        apiMessageDto.setData(breedDto);
        apiMessageDto.setMessage("Get the breed success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateBreedForm createBreedForm, BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var breed = breedMapper.fromCreateFormToEntity(createBreedForm);

        breedRepository.save(breed);
        apiMessageDto.setMessage("Create new breed success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateBreedForm updateBreedForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Breed breed = breedRepository.findById(updateBreedForm.getBreedId()).orElse(null);
        if (breed == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Breed does not exist to update");
            return apiMessageDto;
        }
        breedMapper.fromUpdateFormToEntity(updateBreedForm, breed);

        breedRepository.save(breed);
        apiMessageDto.setMessage("Update breed success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var breed = breedRepository.findById(id).orElse(null);
        if (breed == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Breed does not exist to delete");
            return apiMessageDto;
        }
        breedRepository.deleteById(id);
        apiMessageDto.setMessage("Delete breed success");
        return apiMessageDto;
    }

}
