package site.hirecruit.hr.health

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController @RequestMapping("health")
class HealthCheckController {

    @GetMapping
    fun healthCheck(): ResponseEntity<Map<String, String>> = ResponseEntity.ok(mapOf("message" to "HiRecruit server running"))

    @GetMapping("/error")
    fun cloudWatchErrorFilterHealthCheck(): ResponseEntity<Map<String, String>>{
        log.error { "======== cloudWatch가 error 로그를 잘 감지하는지 테스트 하는 로그 입니다 ========" }
        return ResponseEntity.ok(mapOf("message" to "error log 발생 성공"))
    }
}