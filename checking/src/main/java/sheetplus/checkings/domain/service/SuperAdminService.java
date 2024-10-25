package sheetplus.checkings.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.dto.MemberRequestDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.domain.entity.AdminAcceptCons;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.entity.enums.AcceptCons;
import sheetplus.checkings.domain.entity.enums.MemberType;
import sheetplus.checkings.domain.repository.AdminAcceptConsRepository;
import sheetplus.checkings.domain.repository.MemberRepository;
import sheetplus.checkings.exception.ApiException;

import java.util.List;

import static sheetplus.checkings.error.ApiError.*;

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
                .memberInfo(MemberInfoDto.builder()
                        .email(newAdmin.getEmail())
                        .name(newAdmin.getName())
                        .major(newAdmin.getMajor())
                        .studentId(newAdmin.getStudentId())
                        .memberType(MemberType.NOT_ACCEPT_ADMIN)
                        .build())
                .build();
    }


    @Transactional(readOnly = true)
    public List<AdminAcceptCons> readAdmins(){
        return adminAcceptConsRepository.findAll();
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
