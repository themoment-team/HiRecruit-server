package site.hirecruit.hr.domain.test_util

import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ActiveProfiles("local")
annotation class LocalTest
