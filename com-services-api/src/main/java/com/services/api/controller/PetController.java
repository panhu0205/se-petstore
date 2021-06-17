package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.pet.PetDto;
import com.services.api.form.pet.CreatePetForm;
import com.services.api.form.pet.UpdatePetForm;
import com.services.api.mapper.PetMapper;
import com.services.api.storage.criteria.PetCriteria;
import com.services.api.storage.model.Breed;
import com.services.api.storage.model.Pet;
import com.services.api.storage.model.Post;
import com.services.api.storage.repository.BreedRepository;
import com.services.api.storage.repository.PetRepository;
import com.services.api.storage.repository.PostRepository;

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
@RequestMapping(value = "/v1/pet")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    PostRepository postRepository;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<PetDto>> list(PetCriteria petCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<PetDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Pet> petPage = petRepository.findAll(petCriteria.getSpecification(), pageable);

        ResponseListObj<PetDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(petMapper.fromEntityListToDtoList(petPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(petPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List pets success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PetDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<PetDto> apiMessageDto = new ApiMessageDto<>();
        var pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get pet");
            return apiMessageDto;
        }
        var petDto = petMapper.fromEntityToDto(pet);
        apiMessageDto.setData(petDto);
        apiMessageDto.setMessage("Get the pet success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePetForm createPetForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var pet = petMapper.fromCreateFormToEntity(createPetForm);
        Breed breed = breedRepository.findById(createPetForm.getPetBreedId()).orElse(null);
        if (breed == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Breed does not exist");
            return apiMessageDto;
        }
        pet.setBreed(breed);
        Post post = postRepository.findById(createPetForm.getPetPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post does not exist");
            return apiMessageDto;
        }
        pet.setPost(post);
        petRepository.save(pet);
        apiMessageDto.setMessage("Create new pet success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdatePetForm updatePetForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var pet = petRepository.findById(updatePetForm.getPetId()).orElse(null);
        if (pet == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Pet does not exist to update");
            return apiMessageDto;
        }
        petMapper.fromUpdateFormToEntity(updatePetForm, pet);
        Breed breed = breedRepository.findById(updatePetForm.getPetBreedId()).orElse(null);
        if (breed == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Breed does not exist to update");
            return apiMessageDto;
        }
        pet.setBreed(breed);
        Post post = postRepository.findById(updatePetForm.getPetPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post does not exist to update");
            return apiMessageDto;
        }
        pet.setPost(post);
        petRepository.save(pet);
        apiMessageDto.setMessage("Update pet success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Pet does not exist to delete");
            return apiMessageDto;
        }
        petRepository.deleteById(id);
        apiMessageDto.setMessage("Delete pet success");
        return apiMessageDto;
    }

}
