package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.*;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.service.AuthService;
import sheetplus.checking.domain.service.CommonPageService;
import sheetplus.checking.domain.service.MemberCRUDService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final AuthService authService;
    private final CommonPageService commonPageService;

    @PostMapping("public/register")
    public ResponseEntity<MemberCreateResponseDto> createMember(
            @RequestBody MemberRequestDto memberRequestDto){
        Member member = memberCRUDService.createMember(memberRequestDto);
        TokenDto tokenWithData = authService.memberLogin(LoginDto.builder()
                        .id(member.getId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                .build());
        List<ContestInfoResponseDto> contestNames = commonPageService.readContestInfo();
        return ResponseEntity.ok()
                .body(MemberCreateResponseDto.builder()
                        .tokenDto(tokenWithData)
                        .contestInfoResponseDto(contestNames)
                        .build());
    }



    @PatchMapping("private/update")
    public ResponseEntity<MemberUpdateRequestDto> updateMember(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    ){

        MemberUpdateRequestDto updatedMember
                = memberCRUDService.updateMember(memberUpdateRequestDto,
                token.replace("Bearer ", ""));

        return ResponseEntity.ok()
                .body(updatedMember);
    }


    @DeleteMapping("private/delete")
    public ResponseEntity<String> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String token){
        memberCRUDService.deleteMember(token.replace("Bearer ", ""));
        return ResponseEntity.ok()
                .body("삭제 완료");
    }

}
