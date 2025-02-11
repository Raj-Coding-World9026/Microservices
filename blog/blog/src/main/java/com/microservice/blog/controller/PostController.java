package com.microservice.blog.controller;

import com.microservice.blog.entity.Post;
import com.microservice.blog.payload.PostDto;
import com.microservice.blog.repository.PostRepository;
import com.microservice.blog.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postm")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post){
        Post newPost = postService.savePost(post);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public Post getPostByPostId(@PathVariable String postId){
        Post post=postService.findPostById(postId);
        return post;
    }
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name="commentBreaker",fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
        PostDto postDto=postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId,Exception exc){
        System.out.println("Fallback eis executed beacuase service is down :"+exc.getMessage());
        exc.printStackTrace();
        PostDto dto= new PostDto();
        dto.setPostId("1234");
        dto.setTitle("SERVICE DOWN");
        dto.setContent("SERVICE DOWN");
        dto.setDescription("SERVICE DOWN");
        return  new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }
}
//we break the bigger application into small small projects and establish a
// communication between them using resttemplate
//load will be split between different servers,tech,