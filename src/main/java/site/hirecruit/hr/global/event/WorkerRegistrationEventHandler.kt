package site.hirecruit.hr.global.event

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import site.hirecruit.hr.domain.auth.service.UserRegistrationRollbackService

private val log = KotlinLogging.logger {  }

/**
 * WorkerRegistrationEvent의 핸들러
 * @author 정시원
 * @since 1.1.2
 */
@Component
class WorkerRegistrationEventHandler(
    private val userRegistrationRollbackService: UserRegistrationRollbackService
) {

    /**
     * Worker생성 트랜젝션이 실패하여 이에 대한 보상 트랜잭션을 수행하는 메서드
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    fun workerRegistrationFailHandler(workerRegistrationEvent: WorkerRegistrationEvent){
        log.error {
            """
                직장인(worker)등록이 실패하였습니다. 보상 트랜잭션을 수행합니다.
                error data: 
                AuthUserInfo = '${workerRegistrationEvent.authUserInfo}'
                WorkerRegistrationDto = '${workerRegistrationEvent.workerRegistrationDto}'
            """.trimIndent()
        }
        log.info("WorkerRegistration에 대한 보상 트랜잭션을 수행합니다.")

        val rollbackSuccessUserInfo = userRegistrationRollbackService.rollback(workerRegistrationEvent.authUserInfo)

        log.info("WorkerRegistration에 대한 보상 트랜잭션이 완료되었습니다. RollbackUserInfo = '${rollbackSuccessUserInfo}'")
    }
}