package com.llye.springsecurity.jwtauthentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        Date issuedAt = Calendar.getInstance().getTime();
        return JWT.create()
                  .withSubject("User Details")
                  .withClaim("email", email)
                  .withIssuedAt(issuedAt)
                  .withIssuer("Joyce Lye")
                  .withExpiresAt(DateUtils.addMinutes(issuedAt, 5)) // the token only valid for 5 minutes
                  .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                                  .withSubject("User Details")
                                  .withIssuer("Joyce Lye")
                                  .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email")
                  .asString();
    }
}
