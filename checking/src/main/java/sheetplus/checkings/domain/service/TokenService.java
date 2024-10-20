package sheetplus.checkings.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.config.security.CustomUserDetailsService;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.entity.Token;
import sheetplus.checkings.domain.repository.MemberRepository;
import sheetplus.checkings.domain.repository.TokenRepository;
import sheetplus.checkings.exception.ApiException;
import sheetplus.checkings.util.JwtUtil;

import static sheetplus.checkings.error.ApiError.MEMBER_NOT_FOUND;
import static sheetplus.checkings.error.ApiError.TOKEN_NOT_VALID;

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

        Long memberId = jwtUtil.getMemberId(refreshToken);
        refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(TOKEN_NOT_VALID));

        LoginDto loginDto = customUserDetailsService.loadUserByMemberId(memberId);


        String newAccessToken = jwtUtil.createAccessToken(loginDto);
        String newRefreshToken = jwtUtil.createRefreshToken(loginDto);
        log.info("새로운 access token: {}, 새로운 refresh token: {}",
                newAccessToken, newRefreshToken);

        refreshTokenRepository.findById(memberId)
                .ifPresent(refreshTokenRepository::delete);
        refreshTokenRepository.save(Token.builder()
                        .id(memberId)
                        .refreshToken(newRefreshToken)
                        .build());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));


        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .memberInfo(MemberInfoDto.builder()
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