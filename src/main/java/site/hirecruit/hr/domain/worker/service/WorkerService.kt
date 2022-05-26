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

    /**
     * [AuthUserInfo]와 [WorkerDto.Registration]를 기반으로 [WorkerEntity]를 생성 및 저장한다.
     *
     * @return Worker의 정보
     */
    fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info

    /**
     * [AuthUserInfo]를 기반으로 Worker에 대한 정보를 찾는다.
     *
     * @return Worker의 정보
     */
    fun findWorkerByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info

    /**
     * [AuthUserInfo]정보를 기반으로 조회한 WorkerEntity를 변경한다.
     */
    fun update(authUserInfo: AuthUserInfo, updateDto: WorkerDto.Update)

}