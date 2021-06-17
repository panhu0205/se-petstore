package com.services.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.post.PostDto;
import com.services.api.form.post.CreatePostForm;
import com.services.api.form.post.UpdatePostForm;
import com.services.api.mapper.PostMapper;
import com.services.api.storage.criteria.PostCriteria;
import com.services.api.storage.model.Post;
import com.services.api.storage.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/post")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<PostDto>> list(PostCriteria postCriteria, Pageable pageable) {

        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to list");
        // }

        ApiMessageDto<ResponseListObj<PostDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Post> postPage = postRepository.findAll(postCriteria.getSpecification(), pageable);

        ResponseListObj<PostDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(postMapper.fromEntityListToDtoList(postPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(postPage.getTotalPages());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List posts success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PostDto> get(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to get");
        // }

        ApiMessageDto<PostDto> apiMessageDto = new ApiMessageDto<>();
        var post = postRepository.findById(id).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get post");
            return apiMessageDto;
        }
        var postDto = postMapper.fromEntityToDto(post);
        apiMessageDto.setData(postDto);
        apiMessageDto.setMessage("Get the post success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePostForm createPostForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to create");
        // }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var post = postMapper.fromCreateFormToEntity(createPostForm);

        postRepository.save(post);
        apiMessageDto.setMessage("Create new post success");

        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdatePostForm updatePostForm,
            BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var post = postRepository.findById(updatePostForm.getPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post does not exist to update");
            return apiMessageDto;
        }
        postMapper.fromUpdateFormToEntity(updatePostForm, post);

        postRepository.save(post);
        apiMessageDto.setMessage("Update post success");

        return apiMessageDto;
    }

    @Transactional
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable Long id) {
        // if (!isAdmin()) {
        // throw new UnauthorizationException("Not allowed to delete");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        var post = postRepository.findById(id).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Post does not exist to delete");
            return apiMessageDto;
        }
        postRepository.deleteById(id);
        apiMessageDto.setMessage("Delete post success");
        return apiMessageDto;
    }

}
