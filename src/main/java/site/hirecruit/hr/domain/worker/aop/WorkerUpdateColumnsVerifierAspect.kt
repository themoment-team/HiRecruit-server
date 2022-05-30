package site.hirecruit.hr.domain.worker.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * Worker 필드의 값을 유요한지 확인하는 AOP
 *
 * @author 정시원
 * @since 1.0
 */
@Component
@Aspect
class WorkerUpdateColumnsVerifierAspect {

    @Pointcut("execution(* site.hirecruit.hr.domain.worker.service.AuthUserWorkerService+.updateWorkerEntityByAuthUserInfo(..))")
    private fun authUserWorkerService_updateMethodPointCut(){}

    @Before("authUserWorkerService_updateMethodPointCut()")
    fun validationRegistrationDto(joinPoint: JoinPoint){
        joinPoint.args.asList().forEach {
            if(it is WorkerDto.Update){
                verifyUpdateDtoProperty(it)
                return@forEach
            }
        }
    }

    fun verifyUpdateDtoProperty(updateDto: WorkerDto.Update){
        updateDto.updateColumns.forEach {
            when(it) {
                WorkerDto.Update.Column.COMPANY_ID      -> verifyCompanyIdColumn(updateDto.companyId)
                WorkerDto.Update.Column.INTRODUCTION    -> verifyIntroduction(updateDto.introduction)
                WorkerDto.Update.Column.GIVE_LINK       -> verifyGiveLink(updateDto.giveLink)
                WorkerDto.Update.Column.DEV_YEAR        -> verifyDevYear(updateDto.devYear)
                WorkerDto.Update.Column.POSITION        -> verifyPositionColumn(updateDto.position)
            }
        }
    }

    private fun verifyCompanyIdColumn(companyId: Long?){
        if(companyId == null)
            throw IllegalArgumentException("'companyId' must not be null.")
    }

    private fun verifyIntroduction(introduction: String?){
        if(introduction == null) return
        if(introduction.isBlank()) throw IllegalArgumentException("'introduction' must not be blank. introduction='$introduction'")
    }

    private fun verifyGiveLink(giveLink: String?){
        if(giveLink == null) return
        if(giveLink.isBlank()) throw IllegalArgumentException("'giveLink' must not be blank. giveLink='$giveLink'")
    }

    private fun verifyDevYear(devYear: Int?) {
        if(devYear == null) return
        if(devYear <= -1) throw IllegalArgumentException("'devYear' must not be negative, devYear='$devYear'")
    }

    private fun verifyPositionColumn(position: String?) {
        if(position == null || position.isBlank())
            throw IllegalArgumentException("'position' must not be blank. location='$position'")
    }
}