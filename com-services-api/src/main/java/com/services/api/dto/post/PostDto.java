package com.services.api.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostDto {
    @ApiModelProperty(name = "postId")
    private Long postId; 
    @ApiModelProperty(name = "postTitle")
    private String postTitle;
    @ApiModelProperty(name = "postImage")
    private String postImage;
    @ApiModelProperty(name = "postShortDescription")
    private String postShortDescription;
    @ApiModelProperty(name = "postLongDescription")
    private String postLongDescription;

}
