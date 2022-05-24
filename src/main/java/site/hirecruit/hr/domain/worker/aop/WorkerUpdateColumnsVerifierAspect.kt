package site.hirecruit.hr.domain.worker.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.worker.dto.WorkerDto

@Component
@Aspect
class WorkerUpdateColumnsVerifierAspect {

    @Pointcut("execution(* site.hirecruit.hr.domain.worker.service.WorkerService+.update(...))")
    private fun workerService_updateMethodPointCut(){}

    @Before("workerService_updateMethodPointCut()")
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
                WorkerDto.Update.Column.COMPANY         -> verifyCompanyColumn(updateDto.company)
                WorkerDto.Update.Column.LOCATION        -> verifyLocationColumn(updateDto.location)
                WorkerDto.Update.Column.INTRODUCTION    -> verifyIntroduction(updateDto.introduction)
                WorkerDto.Update.Column.GIVE_LINK       -> verifyGiveLink(updateDto.giveLink)
                WorkerDto.Update.Column.DEV_YEAR        -> verifyDevYear(updateDto.devYear)
            }
        }
    }

    private fun verifyCompanyColumn(company: String?){
        if(company == null || company.isBlank())
            throw IllegalArgumentException("company must not be blank. company='$company'")
    }

    private fun verifyLocationColumn(location: String?) {
        if(location == null || location.isBlank())
            throw IllegalArgumentException("location must not be blank. location='$location'")
    }

    private fun verifyIntroduction(introduction: String?){
        if(introduction == null) return
        if(introduction.isBlank()) throw IllegalArgumentException("introduction must not be blank. introduction='$introduction'")
    }

    private fun verifyGiveLink(giveLink: String?){
        if(giveLink == null) return
        if(giveLink.isBlank()) throw IllegalArgumentException("giveLink must not be blank. giveLink='$giveLink'")
    }

    private fun verifyDevYear(devYear: Int?) {
        if(devYear == null) return
        if(devYear <= -1) throw IllegalArgumentException("devYear must not be negative, devYear='$devYear'")
    }
}