package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.LoginDto;
import sheetplus.checking.domain.dto.MemberInfoDto;
import sheetplus.checking.domain.dto.TokenDto;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.error.ErrorCodeIfs;
import sheetplus.checking.exception.ApiException;
import sheetplus.checking.util.JwtUtil;

import static sheetplus.checking.error.ApiError.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenService refreshTokenService;

    // 최초 토큰 생성에 대해서 발급
    @Transactional
    public TokenDto memberLogin(LoginDto loginDto){
        Member member = memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));


        if(!member.getUniversityEmail().equals(loginDto.getEmail())){
            log.info("이메일이 일치하지 않습니다. 토큰 변조 위험이 있습니다.");
            throw new ApiException(new ErrorCodeIfs() {
                @Override
                public Integer getHttpStatusCode() {
                    return 403;
                }

                @Override
                public String getErrorDescription() {
                    return "이메일이 일치하지 않습니다. 토큰 변조 위험이 있습니다.";
                }
            });
        }


        String accessToken = jwtUtil.createAccessToken(loginDto);
        String refreshToken = jwtUtil.createRefreshToken(loginDto);


        refreshTokenService.saveRefreshToken(loginDto.getId(), refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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

    @Transactional
    public TokenDto refreshTokens(String refreshToken){
        return refreshTokenService.refreshTokens(refreshToken);
    }

}
