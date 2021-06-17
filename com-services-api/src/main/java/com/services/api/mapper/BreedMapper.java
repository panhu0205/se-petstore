package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.breed.BreedDto;
import com.services.api.form.breed.CreateBreedForm;
import com.services.api.form.breed.UpdateBreedForm;
import com.services.api.storage.model.Breed;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreedMapper {

    @Mapping(source = "id", target = "breedId")
    @Mapping(source = "name", target = "breedName")
    @Mapping(source = "description", target = "breedDescription")
    BreedDto fromEntityToDto(Breed breed);

    @Mapping(source = "breedName", target = "name")
    @Mapping(source = "breedDescription", target = "description")
    Breed fromCreateFormToEntity(CreateBreedForm createBreedForm);

    @Mapping(source = "breedId", target = "id")
    @Mapping(source = "breedName", target = "name")
    @Mapping(source = "breedDescription", target = "description")
    void fromUpdateFormToEntity(UpdateBreedForm updateBreedForm, @MappingTarget Breed breed);

    @IterableMapping(elementTargetType = BreedDto.class)
    List<BreedDto> fromEntityListToDtoList(List<Breed> breedList);
}
