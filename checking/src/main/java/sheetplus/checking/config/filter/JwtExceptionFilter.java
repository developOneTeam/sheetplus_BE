package sheetplus.checking.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import sheetplus.checking.error.ErrorCodeIfs;
import sheetplus.checking.error.TokenError;
import sheetplus.checking.exception.JwtException;
import sheetplus.checking.response.Api;

import java.io.IOException;

/**
 * 인증 필터에서 발생하는 JwtException을 잡는 필터입니다.
 * 스프링 필터체인에서 발생하는 에러는 exception 핸들러에서 잡아내지 못하므로(스프링 컨텍스트 밖이기 때문..?)
 * 따로 필터를 만들어서 예외를 처리합니다.
 * 이 필터에서 filterChain.doFilter 를 진행하다가 예외가 터지는걸 잡습니다.
 */

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


    }


}