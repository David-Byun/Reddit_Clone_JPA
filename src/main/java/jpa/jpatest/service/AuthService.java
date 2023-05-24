package jpa.jpatest.service;

import jpa.jpatest.dto.RegisterRequest;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.VerificationToken;
import jpa.jpatest.repository.UserRepository;
import jpa.jpatest.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        Member member = new Member();
        member.setUsername(registerRequest.getUsername());
        member.setEmail(registerRequest.getEmail());
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        member.setCreated(LocalDateTime.now());
        member.setEnabled(false);
        userRepository.save(member);

        generateVerificationToken(member);

        String token = generateVerificationToken(member);
    }

    private String generateVerificationToken(Member member) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setMember(member);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
