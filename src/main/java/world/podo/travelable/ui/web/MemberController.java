package world.podo.travelable.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import world.podo.travelable.application.MemberApplicationService;
import world.podo.travelable.application.MemberAssembler;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberApplicationService memberApplicationService;
    private final MemberAssembler memberAssembler;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberSimpleResponse>> getMe(
            @RequestHeader("Authorization") String authorization,
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
