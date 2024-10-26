package sheetplus.checkings.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import sheetplus.checkings.config.security.CustomUserDetailsService;
import sheetplus.checkings.exception.exceptionMethod.JwtException;
import sheetplus.checkings.util.JwtUtil;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static sheetplus.checkings.exception.error.TokenError.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final List<String> AUTH_WHITELIST = Arrays.asList(
            "swagger-ui", "api-docs", "swagger-ui-custom.html",
            "swagger-ui.html", "public"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, JwtException, AccessDeniedException {

        String requestUri = request.getRequestURI();
        log.info("JwtAuthFilter request : {}" , requestUri);

        if(requestUri.split("/").length > 1 &&
                AUTH_WHITELIST.contains(requestUri.split("/")[1]) ||
                requestUri.equals("/v3/api-docs/swagger-config") ||
                requestUri.equals("/v3/api-docs")){
            log.info("WHITELIST URI 접근, 내부로직 실행");
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            throw new JwtException(TOKEN_NOT_FOUND);
        }

        String accessToken = authorizationHeader.substring(7);

        if (jwtUtil.validateToken(accessToken)) {
            Long userId = jwtUtil.getMemberId(accessToken);
            UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(userId.toString());
            if(userDetails == null){
                throw new JwtException(INVALID_TOKEN);
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            checkUserRole(requestUri, userDetails);
        }
        filterChain.doFilter(request, response);
    }

    private void checkUserRole(String requestUri, UserDetails userDetails) {
        if (requestUri.startsWith("/private/student") &&
                (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))
                && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        && !userDetails.getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")))){
            throw new AccessDeniedException("STUDENT 이상의 권한 필요");

        } else if (requestUri.startsWith("/private/admin") &&
                (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")))) {
            log.info("{}", userDetails.getAuthorities());
            throw new AccessDeniedException("ADMIN 이상의 권한 필요");
        } else if (requestUri.startsWith("/private/super/admin") &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {

            throw new AccessDeniedException("SUPER_ADMIN 권한 필요");
        }
    }

}