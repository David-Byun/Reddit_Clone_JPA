package jpa.jpatest.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@Setter
public class SubReddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;



}
