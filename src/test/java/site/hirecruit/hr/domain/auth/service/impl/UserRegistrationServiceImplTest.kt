package site.hirecruit.hr.domain.auth.service.impl

import io.mockk.*
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserRegistrationDto
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.EmailAuthenticationService
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.global.event.UserRegistrationEvent
import kotlin.random.Random

internal class UserRegistrationServiceImplTest{


    @Test
    fun registrationTest(){
        // Given
        val emailAuthenticationService: EmailAuthenticationService = spyk()
        val userRepository: UserRepository = mockk()
        val publisher: ApplicationEventPublisher = spyk()
        val userRegistrationServiceImpl = UserRegistrationServiceImpl(emailAuthenticationService, userRepository, publisher)

        val userRegistrationDto = UserRegistrationDto(
            email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = RandomString.make(5),
            workerDto = WorkerDto.Registration(
                company = RandomString.make(5),
                location = RandomString.make(5),
            )
        )
        val tempUserAuthUserInfo = AuthUserInfo( // 임시 유저에 대한 인증 객체
            githubId = Random.nextLong(),
            name = RandomString.make(5),
            email = null,
            profileImgUri = RandomString.make(),
            Role.GUEST
        )
        val userEntity = UserEntity(
            githubId = tempUserAuthUserInfo.githubId,
            name = userRegistrationDto.name!!,
            email = userRegistrationDto.email,
            profileImgUri = tempUserAuthUserInfo.profileImgUri,
            role = Role.UNAUTHENTICATED_EMAIL
        )

        /**
         * User를 저장하는 UserRepository 이외에는 메서드에 대한 리턴값이 필요하지 않다.
         */
        every { userRepository.save(userEntity) } answers {userEntity}

        // when
        val authUserInfo = userRegistrationServiceImpl.registration(tempUserAuthUserInfo, userRegistrationDto)

        //then
        verify(exactly = 1) {userRepository.save(userEntity)}
        verify(exactly = 1) {emailAuthenticationService.send(tempUserAuthUserInfo, userRegistrationDto.email)}
        val userRegistrationEvent = UserRegistrationEvent(tempUserAuthUserInfo.githubId, userRegistrationDto.workerDto)
        verify(exactly = 1) {publisher.publishEvent(userRegistrationEvent)}

        assertNotEquals(tempUserAuthUserInfo, authUserInfo) // 회원가입한 유저의 정보와 임시 유저일 떄의 정보는 다르다.
    }
}