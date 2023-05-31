package jpa.jpatest.service;

import jpa.jpatest.dto.CommentsDto;
import jpa.jpatest.exception.PostNotFoundException;
import jpa.jpatest.mapper.CommentMapper;
import jpa.jpatest.model.Comment;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.NotificationEmail;
import jpa.jpatest.model.Post;
import jpa.jpatest.repository.CommentRepository;
import jpa.jpatest.repository.PostRepository;
import jpa.jpatest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    //service 단에도 자유롭게 injection 가능
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getMember().getUsername() + " posted");
        sendCommentNotification(message, post.getMember());
    }

    private void sendCommentNotification(String message, Member member) {
        mailService.sendMail(new NotificationEmail(member.getUsername() + " Commented", member.getEmail(), message));
    }
}
