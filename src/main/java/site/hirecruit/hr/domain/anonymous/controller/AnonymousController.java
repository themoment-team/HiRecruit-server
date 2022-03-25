package site.hirecruit.hr.domain.anonymous.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/anonymous/")
public class AnonymousController {

    /*
    TODO
        1. 익명 UUID로 익명 정보 조회
     */

    @GetMapping("")
    public void findAnonymousByUUID(@RequestParam(value = "id") String anonymousId){

    }

}
