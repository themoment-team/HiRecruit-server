package site.hirecruit.hr.domain.anonymous.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.service.AnonymousService;

@RestController
@RequestMapping("/api/v1/anonymous/")
@RequiredArgsConstructor
public class AnonymousController {

    private final AnonymousService anonymousService;

    /*
    TODO
        1. 익명 UUID로 익명 정보 조회
     */

    @GetMapping("")
    public ResponseEntity<AnonymousDto.AnonymousResponseDto> findAnonymousByUUID(@RequestParam(value = "uuid") String anonymousUUID){
        final AnonymousDto.AnonymousResponseDto anonymousByUUID = anonymousService.findAnonymousByUUID(anonymousUUID);

        //TODO hateoas

        return ResponseEntity.ok().body(anonymousByUUID);
    }

}
