package jpa.jpatest.repository;

import jpa.jpatest.model.Comment;
import jpa.jpatest.model.Post;
import jpa.jpatest.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByMember(Member member);
}
