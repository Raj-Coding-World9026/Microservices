package com.microservice.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String postId;
    private String title;
    private String description;
    private String content;
}
