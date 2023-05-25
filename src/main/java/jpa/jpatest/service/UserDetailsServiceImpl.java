package jpa.jpatest.service;

import jpa.jpatest.model.Member;
import jpa.jpatest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userOptional = userRepository.findByUsername(username);
        Member member = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : " + username));
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                member.isEnabled(), true, true, true, getAuthorities("USER")
        );


    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}
