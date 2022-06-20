package site.hirecruit.hr.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.mentor.service.MentorService

@RestController
@RequestMapping("/api/v1/mentor")
class MentorController(
    private val mentorServiceImpl: MentorService
) {

    @PatchMapping("/process")
    fun executeMentorPromotion(workerId: Long): ResponseEntity<Map<String, String>> {
        // service 실행
        mentorServiceImpl.mentorPromotionProcess(workerId)

        // ok response
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf("msg" to "workerId: $workerId 에게 성공적으로 인증번호를 보냈습니다.")
            )
    }
}