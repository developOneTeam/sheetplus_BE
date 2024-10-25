package sheetplus.checkings.domain.token.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.response.MemberInfoResponseDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.config.security.CustomUserDetailsService;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.token.entity.Token;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.token.repository.TokenRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.JwtUtil;

import static sheetplus.checkings.exception.error.ApiError.MEMBER_NOT_FOUND;
import static sheetplus.checkings.exception.error.ApiError.TOKEN_NOT_VALID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final JwtUtil jwtUtil;
    private final TokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final MemberRepository memberRepository;

    @Value("${jwt.refresh.expiration_time}")
    private int refreshTokenExpire;

    /**
     * 리프래시 토큰 갱신 로직
     */
    @Transactional
    public TokenDto refreshTokens(String refreshToken, HttpServletResponse response) {
        Long memberId = jwtUtil.getMemberId(refreshToken);
        Token findToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(TOKEN_NOT_VALID));

        if(!findToken.getRefreshToken().equals(refreshToken)){
            throw new ApiException(TOKEN_NOT_VALID);
        }
        log.info("request Token: {} ", refreshToken);
        log.info("find Token: {}", findToken);

        LoginDto loginDto = customUserDetailsService.loadUserByMemberId(memberId);


        String newAccessToken = jwtUtil.createAccessToken(loginDto);
        String newRefreshToken = jwtUtil.createRefreshToken(loginDto);
        log.info("새로운 access token: {}, \n 새로운 refresh token: {}",
                newAccessToken, newRefreshToken);

        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);
        refreshTokenRepository.save(Token.builder()
                        .id(memberId)
                        .refreshToken(newRefreshToken)
                        .build());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        ResponseCookie refreshCookie = addCookie(newRefreshToken);
        response.addHeader("Set-Cookie", refreshCookie.toString());

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .memberInfo(MemberInfoResponseDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .major(member.getMajor())
                        .studentId(member.getStudentId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                        .build())
                .build();
    }


    /**
     * Refresh token 최초 발행
     */
    @Transactional
    public void saveRefreshToken(Long memberId, String token, HttpServletResponse response) {
        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(Token.builder()
                .id(memberId)
                .refreshToken(token)
                .build());

        ResponseCookie refreshCookie = addCookie(token);
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

    @Transactional
    public void deleteRefreshToken(Long memberId) {
        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);
    }


    private ResponseCookie addCookie(String token) {

        return ResponseCookie.from("refreshToken", token)
                .path("/")
                .domain("localhost")
                .httpOnly(true)
                .maxAge(refreshTokenExpire)
                .secure(true)
                .sameSite("Strict")
                .build();
    }
}