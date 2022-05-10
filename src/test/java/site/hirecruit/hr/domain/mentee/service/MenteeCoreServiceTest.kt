package site.hirecruit.hr.domain.mentee.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.mentee.entity.MenteeEntity
import site.hirecruit.hr.domain.mentee.repository.MenteeRepository

@SpringBootTest
class MenteeCoreServiceTest(
    @Autowired private var menteeRepository: MenteeRepository
) {

    var anonymous: MenteeEntity? = null

    @BeforeEach
    internal fun setUp() {
        val menteeEntity = MenteeEntity(
            null,
            RandomString.make(5),
            "jyeonjyan",
            "jyeonjyan.dev@gmail.com",
            false)


        anonymous = menteeRepository.save(menteeEntity)
    }

    @Test
    fun 멘티가_멘토에게_상담_신청을_한다(){

    }
}