package site.hirecruit.hr.domain.anonymous.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository

@SpringBootTest
class AnonymousVerifyTest(
    @Autowired var anonymousRepository: AnonymousRepository
) {

    var anonymous: AnonymousEntity? = null

    @BeforeEach
    internal fun setUp() {
        val anonymousEntity = AnonymousEntity(
            null,
            RandomString.make(5),
            "jyeonjyan",
            "jyeonjyan.dev@gmail.com",
            false)


        anonymous = anonymousRepository.save(anonymousEntity)
    }

    @Test
    @DisplayName("익명이 상담신청 가능 상태인지 확인한다.")
    fun 익명이_상담신청_가능_상태(){
        println(anonymous.toString())
    }
}