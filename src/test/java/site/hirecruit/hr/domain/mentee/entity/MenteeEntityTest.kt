package site.hirecruit.hr.domain.mentee.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.mentee.repository.MenteeRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository

@SpringBootTest
@LocalTest
internal class MenteeEntityTest{

    // test용  mockUser
    lateinit var mockUserJyeonjyan : UserEntity;

    @BeforeEach
    internal fun setUp(
        @Autowired userRepository: UserRepository
    ) {
        // 멘티를 등록하려면 우선 회원이여야 한다.
        mockUserJyeonjyan = userRepository.findByGithubId(67095821L)!!
    }

    @Test
    @DisplayName("v1.3 erd에 맞춰 MenteeEntity가 정상적으로 저장된다.")
    fun menteeSaveSuccessfullyTest(@Autowired menteeRepository: MenteeRepository){
        // Given :: Mentee 정보 등록
        val menteeEntity = MenteeEntity(null, mockUserJyeonjyan, false)

        // When :: Mentee save
        val mentee = menteeRepository.save(menteeEntity)

        // Then
        assertThat(mentee.menteeId).isNotNull
        assertThat(mentee.user).isEqualTo(mockUserJyeonjyan)
        assertThat(mentee.certified).isEqualTo(false)
    }
}