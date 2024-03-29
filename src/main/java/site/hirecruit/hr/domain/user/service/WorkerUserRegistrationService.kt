package site.hirecruit.hr.domain.user.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.mapper.AuthUserInfoMapper
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.mapper.UserEntityMapper
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.global.event.UserRegistrationEvent

/**
 * 직장인 유저 등록 서비스
 *
 * @author 정시원
 * @since 1.3
 */
@Service("workerUserRegistrationService")
class WorkerUserRegistrationService(
    private val userRepository: UserRepository,
    private val publisher: ApplicationEventPublisher
): UserRegistrationService<RegularUserRegistrationDto> {

    /**
     * role타입이 [Role.WORKER]인 사용자를 등록하는 매서드
     *
     * @return 등록된 정식 사용자의 정보
     */
    @Transactional
    override fun registration(registrationDto: RegularUserRegistrationDto): AuthUserInfo {
        val savedUserEntity = userRepository.save(
            UserEntityMapper.INSTANCE.toEntity(registrationDto, Role.WORKER)
        )

        val registeredAuthUserInfo = AuthUserInfoMapper.INSTANCE.toAuthUserInfo(savedUserEntity)
        // UserRegistrationEvent발생시킴. 타 도메인 로직(ex. workerEntity 생성 등...)은 해당 이벤트의 헨들러가 담당하여 도메인간 느슨한 결합을 유지
        publisher.publishEvent(UserRegistrationEvent(registeredAuthUserInfo, registrationDto.userRegistrationInfo.workerDto))
        return registeredAuthUserInfo
    }

}