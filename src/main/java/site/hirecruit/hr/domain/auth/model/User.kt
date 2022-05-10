package site.hirecruit.hr.domain.auth.model

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

/**
 * 인증/인가 시 유저의 정보를 담고 있는 객체
 *
 * 초기 버전은 세션으로 유저의 정보를 담습니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
open class User(
    val userId: Long,
    val name: String,
    val email: String,
    val profileUri: String,
    val role: WorkerEntity.Role
) {
    constructor(workerEntity: WorkerEntity) :
            this(workerEntity.workerId!!, workerEntity.name, workerEntity.email, workerEntity.profileUri, workerEntity.role)
}