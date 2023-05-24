package jpa.jpatest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private Integer voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "memberId", referencedColumnName = "memberId")
    private Member member;
}
