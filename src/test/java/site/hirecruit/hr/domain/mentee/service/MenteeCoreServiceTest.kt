package site.hirecruit.hr.domain.mentee.service

import net.bytebuddy.utility.RandomString
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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

    var setUpMentee: MenteeEntity? = null

    @BeforeEach
    internal fun setUp() {
        val menteeEntity = MenteeEntity(
            null,
            RandomString.make(5),
            "jyeonjyan",
            "jyeonjyan.dev@gmail.com",
            false)


        setUpMentee = menteeRepository.save(menteeEntity)
    }

    @Test
    @DisplayName("멘티로 등록한다. [valid에는 관심이 없는 테스트]")
    fun 멘티를_등록하면_등록된_정보를_정상적으로_반환한다(
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

    @Test
    fun menteeUUID로_조회하면_해당하는_mentee_정보가_반환된다(
        @Autowired menteeService: MenteeService
    ){
        // when
        val findMentee = setUpMentee?.menteeUUID?.let { menteeService.findMenteeByUUID(it) }

        // then
        assertNotNull(findMentee)
        assertThat(findMentee?.menteeId).isEqualTo(setUpMentee?.menteeId)
    }

}