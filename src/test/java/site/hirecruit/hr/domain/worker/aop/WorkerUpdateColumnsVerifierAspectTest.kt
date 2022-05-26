package site.hirecruit.hr.domain.worker.aop

import io.mockk.mockk
import io.mockk.spyk
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.*
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.service.AuthUserWorkerService
import kotlin.random.Random

@Nested
internal class WorkerUpdateColumnsVerifierAspectTest{

    val proxy: AuthUserWorkerService = spyk() // proxy로 사용될 객체

    val authUserInfoMock: AuthUserInfo = mockk() // 테스트를 위해 AuthUserInfo를 가짜로 만듦

    @Test @DisplayName("만약 모든 컬럼이 유효하다면?")
    internal fun validationRegistrationDtoTest() {

        val factory = AspectJProxyFactory(this.proxy)
        factory.addAspect(WorkerUpdateColumnsVerifierAspect())
        val proxy = factory.getProxy<AuthUserWorkerService>()

        val updateDto = WorkerDto.Update(
            company = RandomString.make(10),
            location = RandomString.make(10),
            introduction = RandomString.make(10),
            giveLink = RandomString.make(10),
            devYear = Random.nextInt(0, 30),
            updateColumns = listOf( // 변경할 컬럼
                WorkerDto.Update.Column.COMPANY,
                WorkerDto.Update.Column.LOCATION,
                WorkerDto.Update.Column.INTRODUCTION,
                WorkerDto.Update.Column.GIVE_LINK,
                WorkerDto.Update.Column.DEV_YEAR
            )
        )
        //when, then
        Assertions.assertDoesNotThrow({
            proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, updateDto) // 테스트를 위해 AuthUserInfo를 가짜로 넣어준다.
        }, "UpdateDto의 데이터 값이 올바르지 않거나, 해당 검증로직이 잘못되었다.")
    }

    @Nested @DisplayName("만약 올바르지 않은 값을 넣는다면? 컬럼명: ")
    inner class ValidationFailTest{

        @Test
        internal fun company() {
            val factory = AspectJProxyFactory(proxy)
            factory.addAspect(WorkerUpdateColumnsVerifierAspect())
            val proxy = factory.getProxy<AuthUserWorkerService>()

            val nullValue = WorkerDto.Update(
                company = null,
                updateColumns = listOf(
                    WorkerDto.Update.Column.COMPANY,
                )
            )
            val blankValue = WorkerDto.Update(
                company = "     ",
                updateColumns = listOf(
                    WorkerDto.Update.Column.COMPANY,
                )
            )

            assertAll({
                assertThrows<IllegalArgumentException>("null 값은 허용되지 않는다.") {
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, nullValue)
                }
                assertThrows<IllegalArgumentException>("빈 값은 허용되지 않는다."){
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, blankValue)
                }
            })

        }

        @Test
        internal fun introduction() {
            val factory = AspectJProxyFactory(proxy)
            factory.addAspect(WorkerUpdateColumnsVerifierAspect())
            val proxy = factory.getProxy<AuthUserWorkerService>()

            val nullValue = WorkerDto.Update(
                introduction = null,
                updateColumns = listOf(
                    WorkerDto.Update.Column.INTRODUCTION,
                )
            )
            val blankValue = WorkerDto.Update(
                introduction = "     ",
                updateColumns = listOf(
                    WorkerDto.Update.Column.INTRODUCTION,
                )
            )

            assertAll({
                assertThrows<IllegalArgumentException>("빈 값은 허용되지 않는다."){
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, blankValue)
                }
            })

        }

        @Test
        internal fun location() {
            val factory = AspectJProxyFactory(proxy)
            factory.addAspect(WorkerUpdateColumnsVerifierAspect())
            val proxy = factory.getProxy<AuthUserWorkerService>()

            val nullValue = WorkerDto.Update(
                location = null,
                updateColumns = listOf(
                    WorkerDto.Update.Column.LOCATION,
                )
            )
            val blankValue = WorkerDto.Update(
                location = "     ",
                updateColumns = listOf(
                    WorkerDto.Update.Column.LOCATION,
                )
            )

            assertAll({
                assertThrows<IllegalArgumentException>("null 값은 허용되지 않는다.") {
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, nullValue)
                }
                assertThrows<IllegalArgumentException>("빈 값은 허용되지 않는다."){
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, blankValue)
                }
            })

        }

        @Test
        internal fun giveLink() {
            val factory = AspectJProxyFactory(proxy)
            factory.addAspect(WorkerUpdateColumnsVerifierAspect())
            val proxy = factory.getProxy<AuthUserWorkerService>()

            val nullValue = WorkerDto.Update(
                giveLink = null,
                updateColumns = listOf(
                    WorkerDto.Update.Column.GIVE_LINK,
                )
            )
            val blankValue = WorkerDto.Update(
                giveLink = "     ",
                updateColumns = listOf(
                    WorkerDto.Update.Column.GIVE_LINK,
                )
            )

            assertAll({
                assertThrows<IllegalArgumentException>("빈 값은 허용되지 않는다."){
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, blankValue)
                }
            })

        }

        @Test
        internal fun devYear() {
            val factory = AspectJProxyFactory(proxy)
            factory.addAspect(WorkerUpdateColumnsVerifierAspect())
            val proxy = factory.getProxy<AuthUserWorkerService>()

            val nullValue = WorkerDto.Update(
                devYear = null,
                updateColumns = listOf(
                    WorkerDto.Update.Column.DEV_YEAR,
                )
            )
            val blankValue = WorkerDto.Update(
                devYear = -1,
                updateColumns = listOf(
                    WorkerDto.Update.Column.COMPANY,
                )
            )

            assertAll({
                assertThrows<IllegalArgumentException>("음수는 허용되지 않는다."){
                    proxy.updateWorkerEntityByAuthUserInfo(authUserInfoMock, blankValue)
                }
            })

        }
    }
}