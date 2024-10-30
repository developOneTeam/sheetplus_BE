package sheetplus.checkings.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    // 필터 검증용
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        LoginDto dto = LoginDto
                .builder()
                .id(member.getId())
                .email(member.getUniversityEmail())
                .memberType(member.getMemberType())
                .build();

        return new CustomUserDetails(dto);
    }

    // 편의 메소드
    public LoginDto loadUserByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        return LoginDto.builder()
                .id(member.getId())
                .email(member.getUniversityEmail())
                .memberType(member.getMemberType())
                .build();
    }
}
