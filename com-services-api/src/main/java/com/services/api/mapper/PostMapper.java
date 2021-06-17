package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.post.PostDto;
import com.services.api.form.post.CreatePostForm;
import com.services.api.form.post.UpdatePostForm;
import com.services.api.storage.model.Post;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "title", target = "postTitle")
    @Mapping(source = "image", target = "postImage")
    @Mapping(source = "shortDescription", target = "postShortDescription")
    @Mapping(source = "longDescription", target = "postLongDescription")
    PostDto fromEntityToDto(Post post);

    @Mapping(source = "postTitle", target = "title")
    @Mapping(source = "postImage", target = "image")
    @Mapping(source = "postShortDescription", target = "shortDescription")
    @Mapping(source = "postLongDescription", target = "longDescription")
    Post fromCreateFormToEntity(CreatePostForm createPostForm);

    @Mapping(source = "postId", target = "id")
    @Mapping(source = "postTitle", target = "title")
    @Mapping(source = "postImage", target = "image")
    @Mapping(source = "postShortDescription", target = "shortDescription")
    @Mapping(source = "postLongDescription", target = "longDescription")
    void fromUpdateFormToEntity(UpdatePostForm updatePostForm, @MappingTarget Post post);

    @IterableMapping(elementTargetType = PostDto.class)
    List<PostDto> fromEntityListToDtoList(List<Post> postList);
}
