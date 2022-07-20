package site.hirecruit.hr.domain.user.service.legacy

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserRegistrationDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.global.event.UserRegistrationEvent

/**
 * 회원가입을 하는 [UserRegistrationService]의 구현체
 *
 * ## notices
 * 해당 로직은 email 인증 로직을 제외함
 *
 * @author 정시원
 * @since 1.0
 */
@Service
@Deprecated("v1.3부터 더 이상 사용하지 않는 유저 등록 서비스 입니다. 해당 서비스는 WorkerUserRegistrationService로 대체됩니다.")
class UserRegistrationServiceImpl(
    private val userRepository: UserRepository,
    private val publisher: ApplicationEventPublisher
): UserRegistrationService {

    /**
     * 회원가입을 진행하는 메서드 [UserRegistrationEvent]가 발생한다.
     *
     * @see site.hirecruit.hr.domain.auth.aop.UserRegistrationAspect
     * @see UserRegistrationEvent
     */
    @Transactional
    override fun registration(authUserInfo: AuthUserInfo, userRegistrationInfo: UserRegistrationDto): AuthUserInfo {
        val userEntity = UserEntity(
            githubId = authUserInfo.githubId,
            githubLoginId = authUserInfo.githubLoginId,
            email = userRegistrationInfo.email,
            name = userRegistrationInfo.name,
            profileImgUri = authUserInfo.profileImgUri,
            role = Role.WORKER
        )
        val savedUserEntity = userRepository.save(userEntity)
        val registrationAuthUserInfo = AuthUserInfo(
            githubId = savedUserEntity.githubId,
            githubLoginId = savedUserEntity.githubLoginId,
            name = savedUserEntity.name,
            email = savedUserEntity.email,
            profileImgUri = savedUserEntity.profileImgUri,
            role = savedUserEntity.role
        )

        // UserRegistrationEvent발생시킴. 타 도메인 로직(ex. workerEntity 생성 등...)은 해당 이벤트의 헨들러가 담당하여 도메인간 느슨한 결합을 유지
        publisher.publishEvent(UserRegistrationEvent(registrationAuthUserInfo, userRegistrationInfo.workerDto))
        return registrationAuthUserInfo
    }
}