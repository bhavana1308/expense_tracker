package org.launchcode.expense_tracker.configure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.launchcode.expense_tracker.models.UserDto;
import org.launchcode.expense_tracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private final Logger logger = LoggerFactory.getLogger(UserAuthProvider.class);
    private final UserService userService;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        //Encode so it is not saved as plain text
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); //1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", user.getId())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .sign(algorithm);

        logger.info("Generated Token: {}", token);// Print the token to console
        return token;
    }


    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto.builder()
                .id(decoded.getClaim("id").asLong())
                .username(decoded.getSubject())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .build();
        logger.info("Token validated successfully for user: {}", user.getUsername());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByUsername(decoded.getSubject());

        user.setId(decoded.getClaim("id").asLong());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


}