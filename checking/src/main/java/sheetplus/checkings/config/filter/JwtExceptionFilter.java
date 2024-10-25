package sheetplus.checkings.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;
import sheetplus.checkings.error.TokenError;
import sheetplus.checkings.exception.ApiException;
import sheetplus.checkings.exception.JwtException;
import sheetplus.checkings.response.Api;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, AccessDeniedException {
        log.info("JWT 예외처리 로직 진입");
        try {
            filterChain.doFilter(request, response);
        }catch (JwtException e) {
            log.info("JWT 예외 발생");
            if (e.getErrorCodeIfs().getHttpStatusCode() == 2000) {
                response(response, TokenError.INVALID_TOKEN);
            } else if (e.getErrorCodeIfs().getHttpStatusCode() == 2001) {
                response(response, TokenError.EXPIRED_TOKEN);
            } else if(e.getErrorCodeIfs().getHttpStatusCode() == 2002){
                response(response, TokenError.TOKEN_NOT_FOUND);
            } else if (e.getErrorCodeIfs().getHttpStatusCode() == 2003) {
                response(response, TokenError.UNSUPPORTED_TOKEN);
            } else if (e.getErrorCodeIfs().getHttpStatusCode() == 2004) {
                response(response, TokenError.ILLEGAL_TOKEN);
            } else {
                response(response, TokenError.TOKEN_EXCEPTION);
            }
        }catch (ApiException e) {
            response(response, e);
        }catch (AccessDeniedException e){
            log.info("접근 권한이 없는 페이지 접근");
            response(response, e);
        }
        log.info("JWT 예외처리 확인 및 응답 설정 완료");

    }

    private void response(HttpServletResponse response, TokenError error) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(error.getHttpStatusCode());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Api.ERROR(error
                .getHttpStatusCode(), error.getErrorDescription())));

    }

    private void response(HttpServletResponse response, ApiException error) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(error.getErrorCodeIfs().getHttpStatusCode());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Api.ERROR(error
                .getErrorCodeIfs().getHttpStatusCode(), error.getErrorDescription())));

    }

    private void response(HttpServletResponse response, AccessDeniedException error) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Api.ERROR(
                HttpStatus.FORBIDDEN.value(), error.getMessage())));

    }

}