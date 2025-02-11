package com.microservice.comment.service;

import com.microservice.comment.config.RestTemplateConfig;
import com.microservice.comment.entity.Comment;
import com.microservice.comment.payload.Post;
import com.microservice.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private RestTemplateConfig restTemplateConfig;
    @Autowired
    private CommentRepository commentRepository;
    public Comment saveComment(Comment comment){
        Post post = restTemplateConfig.getRestTemplate().getForObject("http://POST-SERVICE/api/postm/" + comment.getPostId(), Post.class);
        //above code line eill goto the url  and based on the post id get the post object
        if(post!=null){
            String commentId = UUID.randomUUID().toString();
            comment.setCommentId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        }else {
            return null;
        }
    }

    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
//restTemplate to merge 2 microservices
//create an configuration class with @configuration
// and develop a method with @bean and then restTemplate object is created by using getForObject