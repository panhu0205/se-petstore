package com.services.api.form.post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UpdatePostForm {
    @NotNull(message = "postId cannot be null")
    @ApiModelProperty(name = "postId")
    private Long postId; 
    @NotEmpty(message = "postTitle cannot be empty") 
    @ApiModelProperty(name = "postTitle")
    private String postTitle;
    @NotEmpty(message = "postImage cannot be empty") 
    @ApiModelProperty(name = "postImage")
    private String postImage;
    @NotEmpty(message = "postShortDescription cannot be empty") 
    @ApiModelProperty(name = "postShortDescription")
    private String postShortDescription;
    @NotEmpty(message = "postLongDescription cannot be empty") 
    @ApiModelProperty(name = "postLongDescription")
    private String postLongDescription;
}
