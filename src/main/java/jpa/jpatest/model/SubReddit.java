package jpa.jpatest.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class SubReddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;



}
