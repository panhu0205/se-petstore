package com.services.api.form.breed;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdateBreedForm {
    @NotNull(message = "breedId cannot be null")
    @ApiModelProperty(name = "breedId")
    private Long breedId; 
    @NotEmpty(message = "breedName cannot be empty")
    @ApiModelProperty(name = "breedName")
    private String breedName;
    @NotEmpty(message = "breedDescription cannot be empty")
    @ApiModelProperty(name = "breedDescription")
    private String breedDescription;
}
