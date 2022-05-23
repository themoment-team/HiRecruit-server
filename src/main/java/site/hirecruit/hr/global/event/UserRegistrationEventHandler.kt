package site.hirecruit.hr.global.event

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val log = KotlinLogging.logger {}

/**
 * [UserRegistrationEvent]가 발생하면 해당 헨들러에서 처리한다.
 *
 * @author 정시원
 * @since 1.0
 */
@Component
class UserRegistrationEventHandler{

    /**
     * UserRegistrationEvent가 발생하면 해당 이벤트 객체를 기반으로 Worker를 생성한다.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // event publisher가 commit된 후 해당 event handler가 실행된다.
    fun createWorker(userRegistrationEvent: UserRegistrationEvent){
        log.debug("UserRegistrationEvent activate UserRegistrationEvent='$userRegistrationEvent'")
        TODO("WorkerService(가칭)를 통해 UserRegistrationEvent가 발생할 때 worker를 생성할 예정")
    }
}