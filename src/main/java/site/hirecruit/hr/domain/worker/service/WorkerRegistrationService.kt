package site.hirecruit.hr.domain.worker.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

/**
 * Worker 등록 Service
 *
 * @see site.hirecruit.hr.global.event.UserRegistrationEventHandler
 * @author 정시원
 * @since 1.0
 */
interface WorkerRegistrationService {

    /**
     * [AuthUserInfo]와 [WorkerDto.Registration]를 기반으로 [WorkerEntity]를 생성 및 저장한다.
     *
     * @return Worker의 정보
     */
    fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info

}