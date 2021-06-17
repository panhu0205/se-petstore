package com.services.api.dto.pet;

import com.services.api.dto.breed.BreedDto;
import com.services.api.dto.post.PostDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PetDto {
    @ApiModelProperty(name = "petId")
    private Long petId; 
    @ApiModelProperty(name = "petGender")
    private Integer petGender;    
    @ApiModelProperty(name = "petAge")
    private Integer petAge;    
    @ApiModelProperty(name = "petOrigin")
    private String petOrigin;
    @ApiModelProperty(name = "petBreed")
    private BreedDto petBreed;
    @ApiModelProperty(name = "petPost")
    private PostDto petPost;
}
