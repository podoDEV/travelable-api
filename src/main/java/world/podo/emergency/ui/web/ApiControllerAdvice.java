package world.podo.emergency.ui.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import world.podo.emergency.application.BadRequestException;
import world.podo.emergency.domain.MemberNotFoundException;
import world.podo.emergency.infrastructure.public_api.PublicApiFailedException;
import world.podo.emergency.infrastructure.spring.security.PodoAuthenticationToken;

import java.security.Principal;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {
    @ModelAttribute("memberId")
    public Long resolveMemberId(Principal principal) {
        if (principal instanceof PodoAuthenticationToken) {
            return ((PodoAuthenticationToken) principal).getMemberId();
        }
        return null;
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity handleNotFoundException(MemberNotFoundException ex) {
        log.warn("MemberNotFoundException occurred", ex);
        return ResponseEntity.notFound()
                             .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException ex) {
        log.warn("BadRequestException occurred", ex);
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ApiResponse.error("BadRequest", ex.getMessage()));
    }

    @ExceptionHandler(PublicApiFailedException.class)
    public ResponseEntity handlePublicApiFailedException(PublicApiFailedException ex) {
        log.error("PublicApiFailedException occurred", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ApiResponse.error("ServiceUnavailable", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        log.error("Exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ApiResponse.error("InternalServerError", ex.getMessage()));
    }
}
