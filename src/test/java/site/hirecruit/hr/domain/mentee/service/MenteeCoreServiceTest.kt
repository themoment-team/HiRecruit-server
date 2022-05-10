package site.hirecruit.hr.domain.mentee.service

import net.bytebuddy.utility.RandomString
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.mentee.dto.MenteeDto
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
    fun 멘티가_멘토에게_상담_신청을_한다(
        @Autowired menteeService: MenteeService
    ){

        // Given
        val menteeInfo = MenteeDto.MenteeRegistryFormatDto("jyeonjyan", "s20062@gsm.hs.kr")

        // When
        val registerMentee = menteeService.registerMentee(menteeInfo)

        // Then
        assertNotNull(registerMentee.menteeId)
        assertNotNull(registerMentee.menteeUUID)
        assertNotNull(registerMentee.name)
        assertNotNull(registerMentee.email)
        assertNotNull(registerMentee.emailCertified)
    }
}