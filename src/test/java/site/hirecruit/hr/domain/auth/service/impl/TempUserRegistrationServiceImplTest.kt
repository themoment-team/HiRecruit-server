package site.hirecruit.hr.domain.auth.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import kotlin.random.Random

@LocalTest
internal class TempUserRegistrationServiceImplTest(){

    private fun makeOAuth2Attribute() : OAuthAttributes{
        val attributes = mapOf<String, Any>(
            "id" to Random.nextInt(8),
            "name" to RandomString.make(8),
            "email" to "${RandomString.make(10)}${RandomString.make(6)}.${RandomString.make(3)}}",
            "avatar_url" to RandomString.make()
        )
        return OAuthAttributes.of(
            registrationId = "github",
            userNameAttributeName = "id",
            attributes = attributes
        )
    }

    @Test @DisplayName("임시 유저 저장 테스트")
    fun 임시유저_저장_테스트(){
        // 1. 해당 githubId로 임시 유저가 존재하지 않는다면, (tempUserRepository.existsById(oAuth2Attribute.id).not() == true)
        // 2. 임시 유저 저장(tempUserRepository.save(tempUserEntity)) 로직을 실행한다.
        // given
        val oAuth2Attribute = makeOAuth2Attribute()
        val tempUserRepository: TempUserRepository = mockk()
        val tempUserEntity = TempUserEntity(
            githubId = oAuth2Attribute.id,
            profileImgUri = oAuth2Attribute.profileImgUri
        )
        every { tempUserRepository.existsById(oAuth2Attribute.id) } answers { false }
        every { tempUserRepository.save(tempUserEntity) } answers {tempUserEntity}

        val tempUserRegistrationServiceImpl = TempUserRegistrationServiceImpl(tempUserRepository)

        // when
        tempUserRegistrationServiceImpl.registration(oAuth2Attribute)

        // then
        verify (exactly = 1) { tempUserRepository.save(tempUserEntity) }
    }

}