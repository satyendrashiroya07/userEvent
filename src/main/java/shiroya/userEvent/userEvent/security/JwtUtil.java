package shiroya.userEvent.userEvent.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroya.userEvent.userEvent.userEntity.RoleEntity;
import shiroya.userEvent.userEvent.userEntity.UserEntity;
import shiroya.userEvent.userEvent.userRepo.UserRepo;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final UserRepo userRepo;

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

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
}
