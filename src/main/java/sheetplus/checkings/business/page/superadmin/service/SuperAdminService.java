package sheetplus.checkings.business.page.superadmin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberRequestDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.domain.adminacceptcons.entity.AdminAcceptCons;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.enums.AcceptCons;
import sheetplus.checkings.domain.enums.MemberType;
import sheetplus.checkings.domain.adminacceptcons.repository.AdminAcceptConsRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.token.service.TokenService;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.util.List;

import static sheetplus.checkings.exception.error.ApiError.*;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final AdminAcceptConsRepository adminAcceptConsRepository;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;


    @Transactional
    public TokenDto createAdmin(MemberRequestDto memberRequestDto){
        AdminAcceptCons adminAcceptCons = adminAcceptConsRepository
                .findById(memberRequestDto.getUniversityEmail())
                .orElse(null);

        if(adminAcceptCons != null){
            if(adminAcceptCons.getAcceptCons().equals(AcceptCons.ADMIN_NOT_ACCEPT)){
                throw new ApiException(ADMIN_NOT_ACCEPT);
            }

            throw new ApiException(ADMIN_EXISTS);
        }


        AdminAcceptCons newAdmin = adminAcceptConsRepository.save(
                AdminAcceptCons.builder()
                        .email(memberRequestDto.getUniversityEmail())
                        .name(memberRequestDto.getName())
                        .major(memberRequestDto.getMajor())
                        .studentId(memberRequestDto.getStudentId())
                        .acceptCons(AcceptCons.ADMIN_NOT_ACCEPT)
                        .build()
        );


        return TokenDto.builder()
                .accessToken("")
                .memberInfo(MemberInfoResponseDto.builder()
                        .email(newAdmin.getEmail())
                        .name(newAdmin.getName())
                        .major(newAdmin.getMajor())
                        .studentId(newAdmin.getStudentId())
                        .memberType(MemberType.NOT_ACCEPT_ADMIN)
                        .build())
                .build();
    }


    @Transactional(readOnly = true)
    public List<AdminAcceptCons> readAdmins(Pageable pageable){
        return adminAcceptConsRepository.findAll(pageable).getContent();
    }

    @Transactional
    public MemberRequestDto updateAdminCons(String email){
        AdminAcceptCons adminAcceptCons = adminAcceptConsRepository.findById(email)
                .orElseThrow(() -> new ApiException(ADMIN_NOT_FOUND));

        adminAcceptCons.updateAcceptCons(AcceptCons.ADMIN_ACCEPT);

        return MemberRequestDto.builder()
                .universityEmail(adminAcceptCons.getEmail())
                .name(adminAcceptCons.getName())
                .major(adminAcceptCons.getMajor())
                .studentId(adminAcceptCons.getStudentId())
                .memberType(MemberType.ADMIN)
                .build();
    }

    @Transactional
    public void deleteAdmin(String email){
        if(adminAcceptConsRepository.existsById(email)){
            adminAcceptConsRepository.deleteById(email);
            Member member = memberRepository.findMemberByUniversityEmail(email)
                    .orElse(null);
            if(member != null){
                memberRepository.delete(member);
                tokenService.deleteRefreshToken(member.getId());
            }

        }else{
            throw new ApiException(ADMIN_NOT_FOUND);
        }

    }


}
