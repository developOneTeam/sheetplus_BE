package sheetplus.checking.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import sheetplus.checking.config.security.CustomUserDetailsService;
import sheetplus.checking.error.ErrorCodeIfs;
import sheetplus.checking.error.TokenError;
import sheetplus.checking.exception.JwtException;
import sheetplus.checking.response.Api;
import sheetplus.checking.util.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

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
            }
        }

        log.info("JwtAuthFilter request : {}" , request.getRequestURI());

        try {
            filterChain.doFilter(request, response);
        }catch (JwtException e) {
            if (e.getErrorCodeIfs().getHttpStatusCode() == 2000) {
                response(response, TokenError.INVALID_TOKEN);
            } else if (e.getErrorCodeIfs().getHttpStatusCode() == 2001) {
                response(response, TokenError.EXPIRED_TOKEN);
            }else if (e.getErrorCodeIfs().getHttpStatusCode() == 2003) {
                response(response, TokenError.AUTHORIZATION_TOKEN_NOT_FOUND);
            } else if (e.getErrorCodeIfs().getHttpStatusCode() == 2004) {
                response(response, TokenError.REFRESH_TOKEN_NOT_VALID);
            } else {
                response(response, TokenError.TOKEN_EXCEPTION);
            }
        }
    }

    private void response(HttpServletResponse response, ErrorCodeIfs errorCodeIfs)
            throws IOException {
        Api apiResponse = Api.ERROR(errorCodeIfs);
        String responseBody = objectMapper.writeValueAsString(apiResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(apiResponse.getResult().getResultCode());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}