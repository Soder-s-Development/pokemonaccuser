package com.juliano.app.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.juliano.app.Models.Account;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class Midleware {
    private final static String key = "cG9rZW1vbldvcmxkSnVsaWFub1NvZGVy";

    static Algorithm algorithm = Algorithm.HMAC256(key);

    JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(key)
            .build();

    public IncomigJWTObject getTokenEValidate(ServletRequest servletRequest){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String authorization = req.getHeader("Authorization");
        return validateToken(authorization.split("Bearer ")[1]);
    }
    private IncomigJWTObject validateToken(String bearer){
        return validJWT(bearer);
    }

    public static String genereteJWT(Account acc){
        return JWT.create()
                .withIssuer("PokemonAccs")
                .withSubject("Account token")
                .withClaim("userId", acc.getId().toString())
                .withClaim("name", acc.getFirst_name())
                .withClaim("email", acc.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);
    }

    public IncomigJWTObject validJWT(String token){
        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            return IncomigJWTObject.builder()
                    .id(decodedJWT.getClaim("userId").asLong())
                    .name(decodedJWT.getClaim("name").asString())
                    .email(decodedJWT.getClaim("email").asString())
                    .build();

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Data
    @Builder
    public class IncomigJWTObject{
        private Long id;
        private String name;
        private String email;
    }
}
