package site.hirecruit.hr.thirdParty.aws.ses.service

import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto

interface HrSesService {
    fun sendEmailWithEmailTemplate(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): Boolean
}