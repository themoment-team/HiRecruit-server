package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import java.util.concurrent.CompletableFuture

/**
 * email인증 서비스
 *
 * @author 정시원
 * @since 1.0.
 */
interface EmailAuthenticationService {

    /**
     * email 인증 메일를 전송한다.
     *
     * @param authUserInfo 인증 메일을 보낼 회원 정보
     * @param email 인증 메일을 보낼 email
     * @return 인증에 사용된 임의의 토큰
     */
    fun send(authUserInfo: AuthUserInfo, email: String): String

    /**
     * email로 전송한 토큰을 검증한다.
     *
     * @return
     * - true: 토큰이 유효함
     * - false: 토큰이 유효하지 않음
     */
    fun tokenVerification(email: String): Boolean
}