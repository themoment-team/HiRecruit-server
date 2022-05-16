package site.hirecruit.hr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HRApplication

fun main(args: Array<String>) {
    runApplication<HRApplication>(*args)
}