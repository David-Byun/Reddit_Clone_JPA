package jpa.jpatest.service;

import jpa.jpatest.dto.AuthenticationResponse;
import jpa.jpatest.dto.LoginRequest;
import jpa.jpatest.dto.RegisterRequest;
import jpa.jpatest.exception.SpringRedditException;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.NotificationEmail;
import jpa.jpatest.model.VerificationToken;
import jpa.jpatest.repository.UserRepository;
import jpa.jpatest.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static jpa.jpatest.util.Constants.ACTIVATION_EMAIL;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;



    @Transactional
    public void signup(RegisterRequest registerRequest) {
        //part1(가입창에서 받은 회원DTO를 회원ENTITY로 변환후 저장한다)
        Member member = new Member();
        member.setUsername(registerRequest.getUsername());
        member.setEmail(registerRequest.getEmail());
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        member.setCreated(LocalDateTime.now());
        member.setEnabled(false);
        userRepository.save(member);

        //part2 토큰생성 > 메일발송
        String token = generateVerificationToken(member);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : " + ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new NotificationEmail("Plz Activate your account", member.getEmail(), message));

    }

    private String generateVerificationToken(Member member) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setMember(member);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        //token이 입력되면 db에서 토큰을 찾는다.
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);

        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));

        //token을 찾으면 해당 토큰의 username을 찾는다.
        String username = verificationTokenOptional.get().getMember().getUsername();

        //해당 username으로 member를 찾는다.
        Member member = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));

        //member 권한을 허용한다.
        member.setEnabled(true);

        //member를 저장한다.
        userRepository.save(member);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        //authenticate를 셋팅해준다.
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //token
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
