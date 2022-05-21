package site.hirecruit.hr.global.event

import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * 회원이 가입을 시도하면 발생하는 이벤트 객체
 *
 * @see site.hirecruit.hr.domain.auth.service.impl.UserRegistrationServiceImpl
 * @author 정시원
 */
class UserRegistrationEvent(
    private val githubId: Long,
    private val workerInfo: WorkerDto.Registration
)