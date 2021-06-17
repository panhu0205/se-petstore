package com.services.api.dto.breed;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BreedDto {
    @ApiModelProperty(name = "breedId")
    private Long breedId; 
    @ApiModelProperty(name = "breedName")
    private String breedName;
    @ApiModelProperty(name = "breedDescription")
    private String breedDescription;
}
