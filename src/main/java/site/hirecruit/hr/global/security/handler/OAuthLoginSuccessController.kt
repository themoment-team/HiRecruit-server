package site.hirecruit.hr.global.security.handler

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("")
@org.springframework.stereotype.Controller
class OAuthLoginSuccessController {

    @GetMapping("/")
    private fun a(): String{
        return "redirect:http://localhost:3000"
    }
}