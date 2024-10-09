package sheetplus.checking.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import sheetplus.checking.config.security.CustomUserDetailsService;
import sheetplus.checking.domain.dto.TokenDto;
import sheetplus.checking.domain.service.TokenService;
import sheetplus.checking.util.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;
        String refreshToken;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        }


        if (accessToken != null) {
            if (jwtUtil.validateToken(accessToken)) {
                Long userId = jwtUtil.getMemberId(accessToken);
                UserDetails userDetails = customUserDetailsService
                        .loadUserByUsername(userId.toString());

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else if (jwtUtil.isTokenExpired(accessToken)) {
                refreshToken = request.getHeader("refresh-token");
                if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
                    try {
                        TokenDto tokenDto = tokenService.refreshTokens(refreshToken);


                        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
                        response.setHeader("refresh-token", tokenDto.getRefreshToken());


                        Long memberId = jwtUtil.getMemberId(tokenDto.getAccessToken());
                        UserDetails userDetails = customUserDetailsService
                                .loadUserByUsername(memberId.toString());

                        if (userDetails != null) {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails,
                                            null, userDetails.getAuthorities());

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (IllegalArgumentException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "유효하지 않은 Refresh Token입니다.");
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}