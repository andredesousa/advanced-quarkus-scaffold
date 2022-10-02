package app.service;

import app.config.UserPrincipal;
import app.dto.AuthDto;
import app.entity.User;
import app.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.quarkus.security.UnauthorizedException;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthService {

    private static final String jwtAuthorities = "authorities";

    private static final String bearerPrefix = "Bearer";

    @ConfigProperty(name = "jwt.secret")
    protected transient String jwtSecret;

    @ConfigProperty(name = "jwt.expiration")
    protected transient Long jwtExpiration;

    @Inject
    protected transient UserRepository userRepository;

    /**
     * Login method.
     * @param auth - The user credentials.
     * @return The user details.
     */
    public UserPrincipal login(AuthDto auth) {
        User user = userRepository.findByUsername(auth.getUsername()).orElseThrow(() -> new UnauthorizedException());
        Result isMatch = BCrypt.verifyer().verify(auth.getPassword().toCharArray(), user.getPassword().toCharArray());

        if (!isMatch.validFormat || !isMatch.verified) {
            throw new UnauthorizedException();
        } else {
            return new UserPrincipal(auth.getUsername(), refresh(user.getUsername(), List.of()), List.of());
        }
    }

    /**
     * Refresh the token.
     * @param username - The user name.
     * @param authorities - The user roles.
     * @return A new token.
     */
    public String refresh(String username, List<String> authorities) {
        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
            .claim(jwtAuthorities, authorities)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    /**
     * Validates the token.
     * @param bearerToken - The bearer token string.
     * @return Authentication object.
     */
    public UserPrincipal validate(String bearerToken) {
        String token = bearerToken != null ? bearerToken.substring(bearerPrefix.length() + 1) : null;
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        List<String> authorities = (List<String>) claims.get(jwtAuthorities);

        return new UserPrincipal(username, token, authorities);
    }
}
