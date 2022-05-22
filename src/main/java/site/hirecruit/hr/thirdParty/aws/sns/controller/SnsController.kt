package site.hirecruit.hr.thirdParty.aws.sns.controller

import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.thirdParty.aws.config.AwsSnsConfig
import site.hirecruit.hr.thirdParty.aws.service.CredentialService

@RestController
class SnsController(
    private val awsSnsConfig: AwsSnsConfig,
    private val credentialService: CredentialService
) {

}