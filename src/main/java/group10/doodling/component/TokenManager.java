package group10.doodling.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenManager implements InitializingBean {

    @Value("${jwt.secret-string}")
    private String JWT_SECRET_STRING;

    @Value("${host-address}")
    private String HOST_ADDRESS;

    private final long EXPIRATION_TIME = 600 * 1000;

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_STRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String userId, String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .issuer(HOST_ADDRESS)
                .subject(username)
                .issuedAt(now)
                .claim("user_id", userId)
                .expiration(expiry)
                .signWith(getSignKey())
                .compact();
    }

    public Jws<Claims> verifyToken(String jws) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(jws);

    }

}
