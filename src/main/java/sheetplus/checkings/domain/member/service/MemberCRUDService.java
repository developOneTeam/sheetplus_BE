package sheetplus.checkings.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberRequestDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.adminacceptcons.repository.AdminAcceptConsRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.temporarymember.repository.TemporaryMemberRepository;
import sheetplus.checkings.domain.token.service.TokenService;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.*;


@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberRepository memberRepository;
    private final AdminAcceptConsRepository adminAcceptConsRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;
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

        if(memberRepository.findMemberByUniversityEmail(member.getUniversityEmail()).isPresent()){
            throw new ApiException(MEMBER_EXISTS);
        }

        if(adminAcceptConsRepository.findById(member.getUniversityEmail()).isPresent()){
            throw new ApiException(ADMIN_EXISTS);
        }

        memberRepository.save(member);
        temporaryMemberRepository.deleteById(member.getUniversityEmail());

        return member;
    }

    @Transactional
    public MemberUpdateRequestDto updateMember(MemberUpdateRequestDto memberUpdateRequestDto, Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        member.memberInfoUpdate(memberUpdateRequestDto);

        return MemberUpdateRequestDto.builder()
                .major(member.getMajor())
                .name(member.getName())
                .studentId(member.getStudentId())
                .build();
    }


    @CacheEvict(value = "member", key = "#id")
    @Transactional
    public void deleteMember(Long id){
        if(memberRepository.existsById(id)){
            memberRepository.deleteById(id);
            tokenService.deleteRefreshToken(id);
        }else{
            throw new ApiException(MEMBER_NOT_FOUND);
        }

    }



}
