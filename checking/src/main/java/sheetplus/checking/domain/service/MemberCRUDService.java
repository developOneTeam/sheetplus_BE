package sheetplus.checking.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.MemberRequestDto;
import sheetplus.checking.domain.dto.MemberUpdateRequestDto;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.util.JwtUtil;


@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Transactional
    public Member createMember(MemberRequestDto memberRequestDto) {
        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .studentId(memberRequestDto.getStudentId())
                .major(memberRequestDto.getMajor())
                .universityEmail(memberRequestDto.getUniversityEmail())
                .memberType(memberRequestDto.getMemberType())
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public MemberUpdateRequestDto updateMember(MemberUpdateRequestDto memberUpdateRequestDto, String token){
        Member member = memberRepository.findById(jwtUtil.getMemberId(token)).orElse(null);

        if(member == null){
            // 예외처리
            return null;
        }
        member.memberInfoUpdate(memberUpdateRequestDto);

        return MemberUpdateRequestDto.builder()
                .major(member.getMajor())
                .name(member.getName())
                .major(member.getStudentId())
                .build();
    }


    @Transactional
    public void deleteMember(String token){
        Long id = jwtUtil.getMemberId(token);
        memberRepository.deleteById(id);
        tokenService.deleteRefreshToken(id);
    }



}
