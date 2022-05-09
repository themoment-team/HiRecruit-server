package site.hirecruit.hr.domain.anonymous.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity
import site.hirecruit.hr.domain.anonymous.repository.AnonymousRepository

@SpringBootTest
class AnonymousVerifyTest(
    @Autowired private var anonymousRepository: AnonymousRepository
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
    fun 익명이_멘토에게_상담_신청을_한다(){

    }
}