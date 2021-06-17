package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.pet.PetDto;
import com.services.api.form.pet.CreatePetForm;
import com.services.api.form.pet.UpdatePetForm;
import com.services.api.storage.model.Pet;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    @Mapping(source = "id", target = "petId")
    @Mapping(source = "gender", target = "petGender")
    @Mapping(source = "age", target = "petAge")
    @Mapping(source = "origin", target = "petOrigin")
    @Mapping(source = "breed.id", target = "petBreed.breedId")
    @Mapping(source = "post.id", target = "petPost.postId")
    PetDto fromEntityToDto(Pet pet);

    @Mapping(source = "petGender", target = "gender")
    @Mapping(source = "petAge", target = "age")
    @Mapping(source = "petOrigin", target = "origin")
    Pet fromCreateFormToEntity(CreatePetForm createPetForm);

    @Mapping(source = "petId", target = "id")
    @Mapping(source = "petGender", target = "gender")
    @Mapping(source = "petAge", target = "age")
    @Mapping(source = "petOrigin", target = "origin")
    void fromUpdateFormToEntity(UpdatePetForm updatePetForm, @MappingTarget Pet pet);

    @IterableMapping(elementTargetType = PetDto.class)
    List<PetDto> fromEntityListToDtoList(List<Pet> petList);
}
