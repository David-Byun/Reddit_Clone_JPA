package jpa.jpatest.service;

import jpa.jpatest.dto.CommentsDto;
import jpa.jpatest.exception.PostNotFoundException;
import jpa.jpatest.model.Comment;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.NotificationEmail;
import jpa.jpatest.model.Post;
import jpa.jpatest.repository.CommentRepository;
import jpa.jpatest.repository.PostRepository;
import jpa.jpatest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    //service 단에도 자유롭게 injection 가능
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

}
