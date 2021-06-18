package com.services.api.form.pet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreatePetForm {

    @NotNull(message = "petGender cannot be null")
    @ApiModelProperty(name = "petGender")
    private Integer petGender;    
    @NotNull(message = "petAge cannot be null")
    @ApiModelProperty(name = "petAge")
    private Integer petAge;   
    @NotEmpty(message = "petOrigin cannot be empty") 
    @ApiModelProperty(name = "petOrigin")
    private String petOrigin;   
    @NotNull(message = "petBreedId cannot be null")
    @ApiModelProperty(name = "petBreedId")
    private Long petBreedId;
    @NotNull(message = "petPostId cannot be null")
    @ApiModelProperty(name = "petPostId")
    private Long petPostId;
}
