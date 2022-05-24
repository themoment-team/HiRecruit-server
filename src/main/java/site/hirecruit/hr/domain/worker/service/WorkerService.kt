package site.hirecruit.hr.domain.worker.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * Worker Service
 *
 * @author 정시원
 * @since 1.0
 */
interface WorkerService {

    fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info

    fun findWorkerByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info

    fun update(updateDto: WorkerDto.Update)



}