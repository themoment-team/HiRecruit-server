package site.hirecruit.hr.thirdParty.aws.ses.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.SesFactoryService

@Service
class SesFactoryServiceImpl: SesFactoryService {
    override fun sendSingleMessageToSingleDestination(sesRequestDto: SesRequestDto) {
        TODO("Not yet implemented")
    }
}