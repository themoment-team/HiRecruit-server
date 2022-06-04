package site.hirecruit.hr.domain.token.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import kotlin.random.Random

internal class TokenServiceTest{

    private val tokenService = TokenService(
        _secretKey = RandomString.make(30),
        accessTokenExpire = 6000,
        refreshTokenExpire = 60000
    )

    fun makeAuthUserInfo(): AuthUserInfo = AuthUserInfo(
        githubId = Random.nextLong(),
        name = RandomString.make(5),
        email = RandomString.make(10),
        profileImgUri = RandomString.make(10),
        role = Role.UNAUTHENTICATED_EMAIL
    )

    @Test @DisplayName("토큰 생성 및 클레임 추출 테스트")
    fun createTokenTest(){
        val authUserInfo = makeAuthUserInfo()

        val token = tokenService.createToken(authUserInfo)
        val accessTokenClaim: Any? = tokenService.getAuthUserInfoByAccessTokenClaim(token.accessToken)

        assertEquals(authUserInfo, accessTokenClaim,
            "토큰 생성에 사용한 AuthUserInfo와 그 토큰에서의 추출한 AuthUserInfo는 다를 수 없습니다.")
    }

    @Test @DisplayName("토큰 검증 로직 테스트")
    fun tokenVerifyTest(){
        val authUserInfo = makeAuthUserInfo()
        val token = tokenService.createToken(authUserInfo)

        val isAccessTokenVerified = tokenService.verifyToken(token.accessToken)

        assertTrue(isAccessTokenVerified)
    }
}