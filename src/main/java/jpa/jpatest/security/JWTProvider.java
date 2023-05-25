package jpa.jpatest.security;

import org.springframework.stereotype.Service;

import java.security.KeyStore;

@Service
public class JWTProvider {

    private KeyStore keyStore;
}
