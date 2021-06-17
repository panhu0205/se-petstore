package com.services.api.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateAccountForm {
    @NotEmpty(message = "username cant not be null")
    @ApiModelProperty(name = "username", required = true)
    private String username;
    @ApiModelProperty(name = "email")
    @Email
    private String email;
    @NotEmpty(message = "password cant not be null")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotEmpty(message = "fullName cant not be null")
    @ApiModelProperty(name = "fullName",example = "Tam Nguyen",required = true)
    private String fullName;
    private String avatarPath;
    @NotNull(message = "groupId cant not be null")
    @ApiModelProperty(name = "groupId", required = true)
    private Long groupId;
    @NotEmpty(message = "phone can not be empty")
    @ApiModelProperty(name = "phone")
    private String phone;
    @NotEmpty(message = "lang can not be empty")
    @ApiModelProperty(name = "lang")
    private String lang;
}
