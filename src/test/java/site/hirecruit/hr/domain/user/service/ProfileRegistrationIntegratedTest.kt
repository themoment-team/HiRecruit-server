package site.hirecruit.hr.domain.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationRequestDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.TempUserEntity
import site.hirecruit.hr.domain.user.repository.TempUserRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.global.data.SessionAttribute
import java.nio.charset.Charset
import java.util.*

/**
 * 프로필 등록 feature 통합테스트
 * @author 정시원
 * @since v1.2.4
 */
@SpringBootTest
@LocalTest
@DisplayName("프로필 등록 통합 테스트")
internal class ProfileRegistrationIntegratedTest(
    @Autowired private val applicationContext: WebApplicationContext,

    @Autowired private val tempUserRepository: TempUserRepository,
    @Autowired private val workerRepository: WorkerRepository,

    @Autowired private val mapper: ObjectMapper,
) {

    private val apiUrl = "/api/v1/user/registration"

    private lateinit var mvc: MockMvc;

    @BeforeEach
    fun setUp(){
        this.mvc = MockMvcBuilders
            .webAppContextSetup(this.applicationContext)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .alwaysDo<DefaultMockMvcBuilder> { MockMvcResultHandlers.print() }
            .defaultResponseCharacterEncoding<DefaultMockMvcBuilder>(Charset.defaultCharset())
            .build()
    }

    @Test @DisplayName("프로필등록이 정상적으로 완료된다면?")
    fun registrationTest(){
        // given
        val tempUserInfo = registrationTempUser()
        val regularUserRegistrationDto = userRequestValue(tempUserInfo)
        val userRequest = regularUserRegistrationDto.userRegistrationInfo
        val userRequestValueAboutWorker = regularUserRegistrationDto.userRegistrationInfo.workerDto

        val oauth2LoginInfo = DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(tempUserInfo.role.name)),
            mapOf(
                "userNameAttributeName" to "id",
                "id"                    to tempUserInfo.githubId,
                "login"                 to tempUserInfo.githubLoginId,
                "registrationId"        to "github"
            ),
            "id"
        )
        // when
        val mvcRequest = mvc.post(apiUrl) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(userRequest)
            sessionAttrs = mapOf(
                SessionAttribute.AUTH_USER_INFO.attributeName to tempUserInfo,
            )
            with( // 이미 임시 유저가 로그인 되어 있다.
                oauth2Login()
                    .authorities(SimpleGrantedAuthority(Role.GUEST.role))
                    .oauth2User(oauth2LoginInfo)
            )
        }
        mvcRequest.andDo { print() }

        // then
        mvcRequest.andExpect {
            status { isCreated() }
            cookie { value("USER_TYPE", Role.WORKER.name) }
            content {
                contentType(MediaType.APPLICATION_JSON)
            }
        }

        val response = mapper.readValue(
            mvcRequest.andReturn().response.contentAsString,
            AuthUserInfo::class.java
        )

        val workerEntity = workerRepository.findByUser_GithubId(response.githubId)
            ?: throw IllegalStateException("WorkerUserRegistrationService를 호출하면 WorkerEntity 존재해야 한다.")

        assertAll("userEntity가 userRequestValue를 기반으로 생성되어야 한다.", {
            assertEquals(regularUserRegistrationDto.githubId, response.githubId)
            assertEquals(regularUserRegistrationDto.githubLoginId, response.githubLoginId)
            assertEquals(regularUserRegistrationDto.profileImgUri, response.profileImgUri)
            assertEquals(regularUserRegistrationDto.userRegistrationInfo.email, response.email)
            assertEquals(regularUserRegistrationDto.userRegistrationInfo.name, response.name)
        })

        assertAll("workerEntity가 userRequestValue를 기반으로 생성되어야 한다.",  {
            assertEquals(userRequestValueAboutWorker.position, workerEntity.position)
            assertEquals(userRequestValueAboutWorker.devYear, workerEntity.devYear)
            assertEquals(userRequestValueAboutWorker.introduction, workerEntity.introduction)
        })

        assertNotNull(
            workerRepository.findByCompany_CompanyId(userRequestValueAboutWorker.companyId)
                .find {
                    it.workerId == workerEntity.workerId
                },
            "사용자가 입력한 companyId와 사용자가 입력한 데이터를 기반으로 생성한 WorkerEntity와 연관관계가 맻어야 한다."
        )

        assertTrue(tempUserRepository.findById(
            regularUserRegistrationDto.githubId).isEmpty,
            "TempUser(임시유저)는 삭제되어야 한다."
        )
    }

    private fun registrationTempUser(): AuthUserInfo{
        val savedTempUser = tempUserRepository.save(
            TempUserEntity(
                githubId = Random().nextLong(),
                githubLoginId = RandomString.make(8),
                profileImgUri = RandomString.make(18)
            )
        )
        return AuthUserInfo(
            githubId = savedTempUser.githubId,
            githubLoginId = savedTempUser.githubLoginId,
            name = "임시유저",
            email = null,
            profileImgUri = savedTempUser.profileImgUri,
            role = Role.GUEST
        )
    }

    private fun userRequestValue(tempUserInfo: AuthUserInfo): RegularUserRegistrationDto =
        RegularUserRegistrationDto(
            githubId = tempUserInfo.githubId,
            githubLoginId = tempUserInfo.githubLoginId,
            profileImgUri = tempUserInfo.profileImgUri,
            userRegistrationInfo = RegularUserRegistrationRequestDto(
                _email = makeEmail(),
                _name = RandomString.make(5),
                workerDto = WorkerDto.Registration(
                    _companyId = kotlin.random.Random.nextLong(1, 5),
                    introduction = RandomString.make(10),
                    devYear = kotlin.random.Random.nextInt(0, 50),
                    position = RandomString.make(10)
                )
            )
        )

    private fun makeEmail() = "${RandomString.make(5)}@${RandomString.make(4)}.${RandomString.make(3)}"
}