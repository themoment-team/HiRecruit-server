package site.hirecruit.hr.batch.github_login_id.service

import com.fasterxml.jackson.annotation.JsonProperty
import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.getForEntity

private val log = KotlinLogging.logger {  }

/**
 * GithubId를 기반으로 LoginId를 찾는 서비스
 *
 * https;//api.github.io/user/{githubId} API를 통해 사용자의 정보를 json형태로 불러올 수 있다.
 * 여기서 사용자가 login할 때 사용하는 id를 login프로피티로 가져올 수 있다. 이를 통해 github profile link를 만들 수 있다.
 *
 * ### API 요청 예시
 * 해당 코드 작성자의 githubId의 값이 '62932968'이고 이를 예시로 들면 https;//api.github.io/user/62932968 에 http요청을 보내면 다음과 같이 응답한다.
 * ```json
 * {
 *   login: "siwony",  // login 프로퍼티로 github login id를 얻어올 수 있다.
 *   id: 62932968,
 *   node_id: "MDQ6VXNlcjYyOTMyOTY4",
 *   avatar_url: "https://avatars.githubusercontent.com/u/62932968?v=4",
 *   gravatar_id: ""
 *   etc...
 * ```
 *
 * @author 정시원
 * @since 1.2
 */
class LockupGithubLoginIdService{

    fun findGithubLoginIdByGithubId(githubId: Long): String {
        val restTemplate = createRestTemplate()
        val response: ResponseEntity<GithubUserApiDto> =
            restTemplate.getForEntity("/$githubId", GithubUserApiDto::class)

        if(response.statusCode.is2xxSuccessful) {
            val githubLoginId = response.body?.githubLoginId
            log.debug { "GithubLoginId = '${githubLoginId}'" }
            return githubLoginId
                ?: throw IllegalStateException("github login id 가 null임")
        }
        else
            throw IllegalStateException("API 호출 실패")
    }

    private fun createRestTemplate() = RestTemplateBuilder()
        .rootUri("https://api.github.com/user")
        .messageConverters(MappingJackson2HttpMessageConverter())
        .build()

    class GithubUserApiDto(
        @field:JsonProperty("login")
        val githubLoginId: String
    )
}