package bank.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHelper {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Генерация ключа // TODO алгоритм шифрования вынести в настройки
    private final long expiration = 3600000; // Время жизни токена (1 час) // TODO значение вынести в настройки

    // Генерация токена
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Добавляем роль пользователя
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Извлечение роли пользователя из токена
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // Проверка, не истёк ли токен
    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());

    }

    // Валидация токена
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}