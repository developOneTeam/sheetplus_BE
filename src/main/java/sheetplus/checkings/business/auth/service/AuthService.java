package sheetplus.checkings.business.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.response.MemberInfoResponseDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.enums.MemberType;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.token.service.TokenService;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.JwtUtil;

import static sheetplus.checkings.exception.error.ApiError.*;

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
        Member member = loginDto.getId() != null ? memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND))
                : memberRepository.findMemberByUniversityEmail(loginDto.getEmail())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        if(!member.getMemberType().equals(loginDto.getMemberType())){
            log.info("타입 불일치 멤버 요청");
            throw new ApiException(MEMBER_TYPE_DISCREPANCY);
        }

        if(!member.getUniversityEmail().equals(loginDto.getEmail())){
            log.info("이메일이 일치하지 않습니다. 토큰 변조 위험이 있습니다.");
            throw new ApiException(MEMBER_EMAIL_DISCREPANCY);
        }

        loginDto.setId(member.getId());
        String accessToken = jwtUtil.createAccessToken(loginDto);
        String refreshToken = jwtUtil.createRefreshToken(loginDto);

        refreshTokenService.saveRefreshToken(loginDto.getId(), refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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

    @Transactional
    public TokenDto refreshTokens(String refreshToken){
        if(refreshToken == null){
            throw new ApiException(REFRESH_TOKEN_EXCEPTION);
        }

        return refreshTokenService.refreshTokens(refreshToken);
    }


    public boolean memberTypeCheck(MemberType memberType){
        if(memberType.equals(MemberType.SUPER_ADMIN)){
//            throw new ApiException(SUPER_ADMIN_REGISTER_BLOCK);
        }

        if(memberType.equals(MemberType.ADMIN) ||
        memberType.equals(MemberType.NOT_ACCEPT_ADMIN)){
            return false;
        }
        return true;
    }





}
