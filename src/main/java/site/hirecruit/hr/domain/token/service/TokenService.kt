package site.hirecruit.hr.domain.token.service

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.token.model.Token
import java.security.Key
import java.util.*

/**
 * token 관련 서비스
 *
 * @author 정시원
 */
@Service
class TokenService(
    @Value("\${auth.token.secret}")
    _secretKey: String,

    @Value("\${auth.token.access-token.expire}")
    private val accessTokenExpire: Int,

    @Value("\${auth.token.refresh-token.expire}")
    private val refreshTokenExpire: Int
){

    private val secretKey: Key = Keys.hmacShaKeyFor(
        Base64.getEncoder().encodeToString(_secretKey.toByteArray()).toByteArray()
    )

    private fun jwtBuilder(): JwtBuilder = Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setIssuedAt(Date())

    /**
     * AccessToken, RefreshToken 생성
     *
     * @return AccessToken, RefreshToken를 감싼 [Token]객체
     */
    fun createToken(authUserInfo: AuthUserInfo): Token =
        Token(createAccessToken(authUserInfo), createRefreshToken())


    private fun createRefreshToken() = jwtBuilder()
        .setExpiration(
            Date(Date().time + refreshTokenExpire)
        )
        .compact()

    private fun createAccessToken(authUserInfo: AuthUserInfo) = jwtBuilder()
        .setClaims(mapOf("authUserInfo" to authUserInfo))
        .setExpiration(
            Date(Date().time + accessTokenExpire)
        )
        .compact()

    /**
     * 유효한 토큰 검증
     */
    fun verifyToken(token: String): Boolean{
        val jwtParser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
        return try {
            jwtParser.parseClaimsJws(token).body
                .expiration
                .after(Date())
        }catch (ex: JwtException){
            false
        }
    }
}
