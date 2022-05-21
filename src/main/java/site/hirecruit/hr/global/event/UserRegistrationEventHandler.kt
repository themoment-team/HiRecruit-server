package site.hirecruit.hr.global.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

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
    @EventListener
    fun createWorker(userRegistrationEvent: UserRegistrationEvent){
        TODO("WorkerService(가칭)를 통해 UserRegistrationEvent가 발생할 때 worker를 생성할 예정")
    }
}