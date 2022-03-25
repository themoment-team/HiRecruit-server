package site.hirecruit.hr.domain.anonymous.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import site.hirecruit.hr.domain.anonymous.dto.AnonymousDto;
import site.hirecruit.hr.domain.anonymous.service.AnonymousService;

import java.net.URI;

/**
 * @version 1.0.0
 * @author 전지환
 */
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

    @PostMapping("")
    public ResponseEntity<AnonymousDto.AnonymousResponseDto> createAnonymous(@RequestBody AnonymousDto.AnonymousRequestDto requestDto){
        final AnonymousDto.AnonymousResponseDto responseDto = anonymousService.saveAnonymous(requestDto);

        // responseEntity.created() required http header uri
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getAnonymousId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }
}
