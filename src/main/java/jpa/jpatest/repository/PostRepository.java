package jpa.jpatest.repository;

import jpa.jpatest.model.Post;
import jpa.jpatest.model.SubReddit;
import jpa.jpatest.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubReddit(SubReddit subReddit);

    List<Post> findByMember(Member member);
}
