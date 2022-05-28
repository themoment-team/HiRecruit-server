package site.hirecruit.hr.domain.auth.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
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

internal class UserRegistrationServiceWithoutEmailAuthenticationTest{


    private val publisher: ApplicationEventPublisher = mockk(relaxed = true)
    private val emailAuthenticationService: EmailAuthenticationService = spyk()

    @Test
    @DisplayName("유저 회원가입 로직(UserRegistrationService.registration(...)) 테스트")
    fun registrationTest(){
        // Given
        val userRepository: UserRepository = mockk()

        val userRegistrationService = UserRegistrationServiceWithoutEmailAuthentication(userRepository, publisher)

        val userRegistrationDto = UserRegistrationDto(
            email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = RandomString.make(5),
            workerDto = WorkerDto.Registration(
                companyName = RandomString.make(5),
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
        val registrationAuthUserInfo = AuthUserInfo(
            githubId = userEntity.githubId,
            name = userEntity.name,
            email = userEntity.email,
            profileImgUri = userEntity.profileImgUri,
            userEntity.role
        )

        /**
         * User를 저장하는 UserRepository 이외에는 메서드에 대한 리턴값이 비즈니스 로직에 영향을 미치지 않는다.
         */
        every { userRepository.save(userEntity) } answers {userEntity}

        // when
        val registeredAuthUserInfo = userRegistrationService.registration(tempUserAuthUserInfo, userRegistrationDto)

        //then
        verify(exactly = 1) {userRepository.save(userEntity)}
        val userRegistrationEvent = UserRegistrationEvent(registrationAuthUserInfo, userRegistrationDto.workerDto)
        verify(exactly = 1) {publisher.publishEvent(userRegistrationEvent)}
        verify(exactly = 0) {emailAuthenticationService.send(any(), any())} // email 인증을 수행하지 않는다.

        // 임시 유저일 떄의 Role과 registration()를 수행한 유저일 때의 Role은 다르다.
        assertAll({
            assertNotEquals(tempUserAuthUserInfo.role, registeredAuthUserInfo.role)
            assertEquals(Role.UNAUTHENTICATED_EMAIL, registeredAuthUserInfo.role)
        })

        // 임시 유저일 때 githubId와 profileImgUri는 registration()을 수행한 후도 같다.
        assertAll({
            assertEquals(tempUserAuthUserInfo.githubId, registeredAuthUserInfo.githubId)
            assertEquals(tempUserAuthUserInfo.profileImgUri, registeredAuthUserInfo.profileImgUri)
        })

        // UserRegistrationDto의 email과 name은 registeredAuthUserInfo와 같다.
        // 만약 name이 null이라면 임시 유저일 떄 name이 기본값이다. 밑에 있는 'registration로직에서_UserRegistrationDtoName이NULL이라면()' 참고
        assertAll({
            assertEquals(userRegistrationDto.email, registeredAuthUserInfo.email)
            assertEquals(userRegistrationDto.name, registeredAuthUserInfo.name)
        })
    }

    @Test
    @DisplayName("유저 회원가입 로직(UserRegistrationService.registration(...))에서 UserRegistrationDto.name이 null이라면?")
    fun registration로직에서_UserRegistrationDtoName이NULL이라면(){
        // Given
        val userRepository: UserRepository = mockk()

        val userRegistrationService = UserRegistrationServiceWithoutEmailAuthentication(userRepository, publisher)

        val userRegistrationDto = UserRegistrationDto(
            email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = null,    // UserRegistrationDto.email = null
            workerDto = WorkerDto.Registration(
                companyName = RandomString.make(5),
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
            name = tempUserAuthUserInfo.name, // 임시 유저가 가지고 있는 name
            email = userRegistrationDto.email,
            profileImgUri = tempUserAuthUserInfo.profileImgUri,
            role = Role.UNAUTHENTICATED_EMAIL
        )

        /**
         * User를 저장하는 UserRepository 이외에는 메서드에 대한 리턴값이 비즈니스 로직에 영향을 미치지 않는다.
         */
        every { userRepository.save(userEntity) } answers {userEntity}

        // when
        val registeredAuthUserInfo = userRegistrationService.registration(tempUserAuthUserInfo, userRegistrationDto)

        // then
        verify(exactly = 1) {userRepository.save(userEntity)}
        assertEquals(tempUserAuthUserInfo.name, registeredAuthUserInfo.name)
    }
}