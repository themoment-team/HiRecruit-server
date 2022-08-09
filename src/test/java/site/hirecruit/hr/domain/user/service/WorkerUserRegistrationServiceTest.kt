package site.hirecruit.hr.domain.user.service

import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.global.event.UserRegistrationEvent

internal class WorkerUserRegistrationServiceTest{

    private val publisher: ApplicationEventPublisher = mockk()
    private val userRepository: UserRepository = mockk()

    private val workerUserRegistrationService = WorkerUserRegistrationService(userRepository, publisher)

    @Test @DisplayName("회원이 성공적으로 등록된다면")
    internal fun registration_if_success() {
        // Given
        val registrationDto: RegularUserRegistrationDto = mockk(relaxed = true)

        val registrationUserEntity = registrationDtoToEntity(registrationDto, Role.WORKER)
        every {
            userRepository.save(registrationDtoToEntity(registrationDto, Role.WORKER))
        } answers { registrationUserEntity }

        val userRegistrationEvent = UserRegistrationEvent(
            regularUserRegistrationDtoToAuthUserInfo(registrationDto, registrationUserEntity.role),
            registrationDto.userRegistrationInfo.workerDto
        )
        every { publisher.publishEvent(userRegistrationEvent) } just runs

        // When
        val result = workerUserRegistrationService.registration(registrationDto)

        /**
         * Then
         *
         * 1. 반환값이 registrationDto를 기반으로 만든 AuthUserInfo와 같아야 한다.
         * 2. '반환값.role'이 [Role.WORKER]여야 한다.
         */
        assertEquals(regularUserRegistrationDtoToAuthUserInfo(registrationDto, Role.WORKER), result)
        assertEquals(Role.WORKER, result.role)

        verify(exactly = 1) { publisher.publishEvent(userRegistrationEvent) }
        verify(exactly = 1) { userRepository.save(registrationUserEntity) }
    }

    private fun registrationDtoToEntity(registrationDto: RegularUserRegistrationDto, role: Role) =
        UserEntity(
            githubId = registrationDto.githubId,
            githubLoginId = registrationDto.githubLoginId,
            email = registrationDto.userRegistrationInfo.email,
            name = registrationDto.userRegistrationInfo.name,
            profileImgUri = registrationDto.profileImgUri,
            role = role
        )

    private fun regularUserRegistrationDtoToAuthUserInfo(registrationDto: RegularUserRegistrationDto, role: Role) =
        AuthUserInfo(
            githubId = registrationDto.githubId,
            githubLoginId = registrationDto.githubLoginId,
            name = registrationDto.userRegistrationInfo.name,
            email = registrationDto.userRegistrationInfo.email,
            profileImgUri = registrationDto.profileImgUri,
            role = role
        )
}