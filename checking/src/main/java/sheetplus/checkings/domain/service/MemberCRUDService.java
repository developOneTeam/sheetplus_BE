package sheetplus.checkings.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.dto.MemberRequestDto;
import sheetplus.checkings.domain.dto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.repository.MemberRepository;
import sheetplus.checkings.exception.ApiException;
import sheetplus.checkings.util.JwtUtil;

import static sheetplus.checkings.error.ApiError.MEMBER_NOT_FOUND;


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
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        if(member == null){
            // 예외처리
            return null;
        }
        member.memberInfoUpdate(memberUpdateRequestDto);

        return MemberUpdateRequestDto.builder()
                .major(member.getMajor())
                .name(member.getName())
                .studentId(member.getStudentId())
                .build();
    }


    @Transactional
    public void deleteMember(String token){
        Long id = jwtUtil.getMemberId(token);
        if(memberRepository.existsById(id)){
            memberRepository.deleteById(id);
            tokenService.deleteRefreshToken(id);
        }else{
            throw new ApiException(MEMBER_NOT_FOUND);
        }

    }



}
