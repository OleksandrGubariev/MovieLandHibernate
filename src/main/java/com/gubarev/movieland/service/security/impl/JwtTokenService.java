package com.gubarev.movieland.service.security.impl;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class JwtTokenService {
    private List<String> logoutSessionTokens = new CopyOnWriteArrayList<>();

    @Value("${token.live.period:2}")
    private int tokenLivePeriod;

    @Value("${jwt.secret:movieland}")
    private String jwtSecret;

    public String generateToken(String login) {
        Date currentDate = new Date();
        Date expireTokenDate = Date.from(currentDate.toInstant().plus(Duration.ofHours(tokenLivePeriod)));
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expireTokenDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            if (logoutSessionTokens.contains(token)) {
                return false;
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported jwt", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed jwt", e);
        } catch (SignatureException e) {
            log.error("Invalid signature", e);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean addLogoutSessionToken(String token) {
        return logoutSessionTokens.add(token);
    }
}
