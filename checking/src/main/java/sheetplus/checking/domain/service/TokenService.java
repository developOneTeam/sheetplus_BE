package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.MemberInfoDto;
import sheetplus.checking.domain.dto.TokenDto;
import sheetplus.checking.config.security.CustomUserDetailsService;
import sheetplus.checking.domain.entity.Token;
import sheetplus.checking.domain.repository.TokenRepository;
import sheetplus.checking.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final JwtUtil jwtUtil;
    private final TokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;


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

        MemberInfoDto memberInfoDto = customUserDetailsService.loadUserByMemberId(memberId);


        String newAccessToken = jwtUtil.createAccessToken(memberInfoDto);
        String newRefreshToken = jwtUtil.createRefreshToken(memberInfoDto);


        refreshTokenRepository.delete(storedToken);
        refreshTokenRepository.save(new Token(memberId, newRefreshToken));

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .memberInfo(memberInfoDto)
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