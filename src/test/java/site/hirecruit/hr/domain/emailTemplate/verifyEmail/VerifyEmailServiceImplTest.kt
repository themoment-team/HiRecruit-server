package site.hirecruit.hr.domain.emailTemplate.verifyEmail.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.security.SecureRandom

internal class VerifyEmailServiceImplTest{

    @Test
    fun secureRandomFunTest(){
        val secureRandom = SecureRandom()
        println(secureRandom.nextInt(9))
    }
}