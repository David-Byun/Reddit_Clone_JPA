package jpa.jpatest.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    //member 객체를 통해서 token을 조회할 수 있는 구조
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
    private Instant expiryDate;
}
