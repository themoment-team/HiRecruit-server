package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService
import site.hirecruit.hr.domain.auth.service.UserRegistrationRollbackService

/**
 * UserRegistrationRollbackService 구현체
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class UserRegistrationRollbackServiceImpl(
    private val userRepository: UserRepository,
    private val tempUserRegistrationService: TempUserRegistrationService
): UserRegistrationRollbackService {

    /**
     * 회원 등록 트랜잭션이 실패 했을 떄 User데이터를 Rollback한다.
     * 일종의 SAGA Pattern의 보상 트랜잭션이다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun rollback(rollbackUserInfo: AuthUserInfo): AuthUserInfo {
        userRepository.deleteByGithubId(rollbackUserInfo.githubId)
        val rollbackTempUserRegistrationData = OAuthAttributes.ofUserRollbackData(rollbackUserInfo)
        tempUserRegistrationService.registration(rollbackTempUserRegistrationData) // 회원등록에 실패하였으므로 임시유저로 rollback
        return AuthUserInfo(
            githubId = rollbackUserInfo.githubId,
            githubLoginId = rollbackUserInfo.githubLoginId,
            name = "임시유저",
            email = rollbackUserInfo.email,
            profileImgUri = rollbackUserInfo.profileImgUri,
            role = Role.GUEST
        )
    }
}