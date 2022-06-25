package site.hirecruit.hr

import mu.KotlinLogging
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger {}

@SpringBootApplication
@EnableBatchProcessing
@EnableAsync
class HRApplication {

    @PostConstruct
    fun applicationTimeZoneSetter() {

        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        TimeZone.setDefault(timeZone)

        log.info { "HiRecruit Backend Application TimeZone was set: ${LocalDateTime.now()}" }
    }
}

fun main(args: Array<String>) {
    runApplication<HRApplication>(*args)
}