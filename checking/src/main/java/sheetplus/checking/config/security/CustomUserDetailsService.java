package sheetplus.checking.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.LoginDto;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.repository.MemberRepository;

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
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 멤버가 없습니다."));

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
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 멤버가 없습니다."));

        return LoginDto.builder()
                .id(member.getId())
                .email(member.getUniversityEmail())
                .memberType(member.getMemberType())
                .build();
    }
}
