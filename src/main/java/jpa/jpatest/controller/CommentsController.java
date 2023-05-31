package jpa.jpatest.controller;

import jpa.jpatest.dto.CommentsDto;
import jpa.jpatest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public void getAllCommentsForPost(@PathVariable Long postId) {
        ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }
}
