package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserRegistrationDto
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.EmailAuthenticationService
import site.hirecruit.hr.domain.auth.service.UserRegistrationService
import site.hirecruit.hr.global.event.UserRegistrationEvent

@Service
class UserRegistrationServiceImpl(
    private val emailAuthenticationService: EmailAuthenticationService,
    private val userRepository: UserRepository,
    private val tempRepository: TempUserRepository,
    private val publisher: ApplicationEventPublisher
): UserRegistrationService {


    override fun registration(authUserInfo: AuthUserInfo, userRegistrationInfo: UserRegistrationDto): AuthUserInfo {
        val userEntity = UserEntity(
            githubId = authUserInfo.githubId,
            email = userRegistrationInfo.email,
            name = userRegistrationInfo.name ?: authUserInfo.name,
            profileImgUri = authUserInfo.profileImgUri,
            Role.UNAUTHENTICATED_EMAIL
        )
        val savedUserEntity = userRepository.save(userEntity)

        emailAuthenticationService.send(authUserInfo, userRegistrationInfo.email) // 비동기 처리 예정

        tempRepository.deleteById(savedUserEntity.githubId) // 임시 유저 제거

        // UserRegistrationEvent발생시킴 이후 타 도메인 로직은 해당 이벤트의 헨들러가 담당하여 도메인간 느슨한 결합을 유지
        publisher.publishEvent(UserRegistrationEvent(savedUserEntity.githubId, userRegistrationInfo.workerDto))
        return AuthUserInfo(
            githubId = savedUserEntity.githubId,
            name = savedUserEntity.name,
            email = savedUserEntity.email,
            profileImgUri = savedUserEntity.profileImgUri,
            role = savedUserEntity.role
        )
    }

}