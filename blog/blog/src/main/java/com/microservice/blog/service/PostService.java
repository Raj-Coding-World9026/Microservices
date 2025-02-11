package com.microservice.blog.service;

import com.microservice.blog.config.RestTemplateConfig;
import com.microservice.blog.entity.Post;
import com.microservice.blog.payload.PostDto;
import com.microservice.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplateConfig restTemplateConfig;
    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post save = postRepository.save(post);
        return save;
    }

    public Post findPostById(String postId) {
        Post byId = postRepository.findById(postId).get();
        return byId;
    }

    public PostDto getPostWithComments(String postId) {
        Post post = postRepository.findById(postId).get();
        ArrayList comments = restTemplateConfig.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/commentm/" + postId, ArrayList.class);
        PostDto postDto= new PostDto();
        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setComments(comments);
        return postDto;

    }
}
