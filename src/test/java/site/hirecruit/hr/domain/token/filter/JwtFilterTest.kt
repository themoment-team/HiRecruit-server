package site.hirecruit.hr.domain.token.filter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.token.service.TokenService
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import kotlin.random.Random

internal class JwtFilterTest{

    private fun makeAuthUserInfo(): AuthUserInfo = AuthUserInfo(
        githubId = Random.nextLong(),
        name = RandomString.make(5),
        email = RandomString.make(10),
        profileImgUri = RandomString.make(10),
        role = Role.UNAUTHENTICATED_EMAIL
    )

    @Test @DisplayName("해당 Filter가 SecurityContext에 유저 정보를 잘 등록하는지 테스트")
    internal fun filterTest(){
        // given
        val tokenService: TokenService = mockk()

        val httpServletRequest = MockHttpServletRequest()
        val httpServletResponse = MockHttpServletResponse()
        val filterChain: FilterChain = MockFilterChain()

        val userAuthUserInfo = makeAuthUserInfo()

        val mockAccessToken = RandomString.make(30)
        httpServletRequest.setCookies(Cookie("ACCESS_TOKEN", mockAccessToken))

        every { tokenService.verifyToken(mockAccessToken) } answers {true}
        every { tokenService.getAuthUserInfoByAccessTokenClaim(mockAccessToken) } answers {userAuthUserInfo}

        // when
        val jwtFilter = JwtFilter(tokenService)
        println(mockAccessToken)
        jwtFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain)

        // then
        verify(exactly = 1) { tokenService.verifyToken(mockAccessToken) }
        verify(exactly = 1) { tokenService.getAuthUserInfoByAccessTokenClaim(mockAccessToken) }

        val authentication =
            SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        assertEquals(userAuthUserInfo, authentication.principal,
        "Security Context에 해당 사용자 정보가 저장되지 않음")
    }
}