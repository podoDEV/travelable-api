package world.podo.emergency.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import world.podo.emergency.application.MemberApplicationService;
import world.podo.emergency.application.MemberAssembler;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberApplicationService memberApplicationService;
    private final MemberAssembler memberAssembler;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberSimpleResponse>> getMe(
            @ApiIgnore @ModelAttribute("memberId") Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.data(
                        memberAssembler.toMemberSimpleResponse(
                                memberApplicationService.getMember(memberId)
                        )
                )
        );
    }
}