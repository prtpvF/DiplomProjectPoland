package pl.diplom.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {


        private final String secret = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";

        public String generateToken( String username, String role){
            Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
            String token = JWT.create().withSubject("User details").withClaim("username", username)
                    .withClaim("role", role)
                    .withIssuedAt(new Date()).withIssuer("free-party")
                    .withExpiresAt(expirationDate)
                    .sign(Algorithm.HMAC256(secret));
            return token;
        }

        public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject("User details")
                    .withIssuer("free-party")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        }



        public String extractTokenFromHeader(HttpServletRequest request) {
            String token = request.getHeader("token");
            if(token != null) {
                return token;
            }
            return null;
        }

}