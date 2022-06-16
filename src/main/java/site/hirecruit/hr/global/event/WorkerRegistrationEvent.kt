package site.hirecruit.hr.global.event

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * WorkerRegistration Event객체
 *
 * @see site.hirecruit.hr.domain.worker.service.WorkerRegistrationService
 * @author 정시원
 * @since 1.1.2
 */
data class WorkerRegistrationEvent(
    val authUserInfo: AuthUserInfo,
    val workerRegistrationDto: WorkerDto.Registration
)
