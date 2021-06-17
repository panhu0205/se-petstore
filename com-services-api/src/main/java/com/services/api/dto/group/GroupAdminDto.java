package com.services.api.dto.group;

import java.util.List;

import com.services.api.dto.ABasicAdminDto;
import com.services.api.dto.permission.PermissionDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupAdminDto extends ABasicAdminDto {

    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "kind")
    private int kind;
    @ApiModelProperty(name = "permissions")
    private List<PermissionDto> permissions;
}
