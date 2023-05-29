package jpa.jpatest.controller;

import jpa.jpatest.dto.PostRequest;
import jpa.jpatest.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-subreddit/{id}")
    public List<PostReponse> getPostsByUsername(String username) {
        return postService.getPostsByUsername(username);
    }

    @GetMapping("/by-user/{name}")
    public List<PostResponse> getPostsByUsername(String username) {
        return postService.getPostsByUsername(username);
    }
}
