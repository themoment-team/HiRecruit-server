package site.hirecruit.hr.domain.worker.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * 인증된 유저에 대한 WorkerService입니다.
 *
 * @author 정시원
 * @since 1.0
 */
interface AuthUserWorkerService {

    /**
     * [AuthUserInfo]를 기반으로 Worker에 대한 정보를 찾는다.
     *
     * @return Worker의 정보
     */
    fun findWorkerEntityByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info

    /**
     * [AuthUserInfo]정보를 기반으로 조회한 WorkerEntity를 변경한다.
     */
    fun updateWorkerEntityByAuthUserInfo(authUserInfo: AuthUserInfo, updateDto: WorkerDto.Update)

}