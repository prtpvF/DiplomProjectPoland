package pl.diplom.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;



@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private String jwtLifetime;
    public String generateToken(String username){
        Duration lifetime = Duration.parse("PT" + jwtLifetime); // Преобразование строки в Duration
        Date expirationDate = Date.from(ZonedDateTime.now().plus(lifetime).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .withIssuer("diplom")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("diplom")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim("username").asString();
    }
}
