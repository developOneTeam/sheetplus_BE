package sheetplus.checkings.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.exception.JwtException;


import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import static sheetplus.checkings.error.TokenError.*;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpire;
    private final long refreshTokenExpire;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access.expiration_time}") long accessTokenExpireTime,
            @Value("${jwt.refresh.expiration_time}") long refreshTokenExpireTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpire = accessTokenExpireTime;
        this.refreshTokenExpire = refreshTokenExpireTime;
    }

    public String createAccessToken(LoginDto loginDto){
        return createToken(loginDto, accessTokenExpire);
    }

    public String createRefreshToken(LoginDto loginDto){
        return createToken(loginDto, refreshTokenExpire);
    }

    private String createToken(LoginDto loginDto, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("memberId", loginDto.getId());
        claims.put("email", loginDto.getEmail());
        claims.put("memberType", loginDto.getMemberType());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getMemberId(String token){
        return parseClaims(token).get("memberId", Long.class);
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("유효하지 않은 토큰 검증", e);
            throw new JwtException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰 검증", e);
            throw new JwtException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰 검증", e);
            throw new JwtException(UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰 양식 전달 검증.", e);
            throw new JwtException(ILLEGAL_TOKEN);
        }
    }

    private Claims parseClaims(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
