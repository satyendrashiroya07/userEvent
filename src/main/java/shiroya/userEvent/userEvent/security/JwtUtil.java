package shiroya.userEvent.userEvent.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shiroya.userEvent.userEvent.userEntity.RoleEntity;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userRepo.UserRepo;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final UserRepo userRepo;

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userId) {

        UserEntity user = userRepo.getByUserId(userId);

        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public String generateToken1(String userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 60 * 60 * 1000L);
        UserEntity user = userRepo.getByUserId(userId);

        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
