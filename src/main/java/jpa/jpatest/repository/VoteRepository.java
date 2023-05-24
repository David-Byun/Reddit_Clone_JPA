package jpa.jpatest.repository;

import jpa.jpatest.model.Post;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndMemberOrderByVoteIdDesc(Post post, Member currentMember);
}
