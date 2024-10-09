package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.LoginDto;
import sheetplus.checking.domain.dto.TokenDto;
import sheetplus.checking.config.security.CustomUserDetailsService;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.Token;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.domain.repository.TokenRepository;
import sheetplus.checking.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final JwtUtil jwtUtil;
    private final TokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final MemberRepository memberRepository;


    /**
     * 리프래시 토큰 갱신 로직
     */
    @Transactional
    public TokenDto refreshTokens(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            log.info("유효하지 않은 Refresh Token입니다.");
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        Long memberId = jwtUtil.getMemberId(refreshToken);
        Token storedToken = refreshTokenRepository.findById(memberId).orElse(null);
        if (storedToken == null || !storedToken.getRefreshToken().equals(refreshToken)) {
            log.info("유효하지 않은 Refresh Token입니다.");
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        LoginDto loginDto = customUserDetailsService.loadUserByMemberId(memberId);


        String newAccessToken = jwtUtil.createAccessToken(loginDto);
        String newRefreshToken = jwtUtil.createRefreshToken(loginDto);


        refreshTokenRepository.delete(storedToken);
        refreshTokenRepository.save(new Token(memberId, newRefreshToken));
        Member member = memberRepository.findById(memberId).orElse(null);


        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .memberInfo(member)
                .build();
    }


    /**
     * Refresh token 최초 발행
     */
    @Transactional
    public void saveRefreshToken(Long memberId, String token) {
        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(Token.builder()
                .id(memberId)
                .refreshToken(token)
                .build());
    }

    @Transactional
    public void deleteRefreshToken(Long memberId) {
        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);
    }
}