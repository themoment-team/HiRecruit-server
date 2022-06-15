package site.hirecruit.hr.global.event

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

/**
 * WorkerRegistration Event객체
 *
 *@see site.hirecruit.hr.domain.worker.service.WorkerRegistrationService
 * @author 정시원
 */
data class WorkerRegistrationEvent(
    val authUserInfo: AuthUserInfo
)
