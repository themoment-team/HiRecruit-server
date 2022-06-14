package site.hirecruit.hr.thirdParty.aws.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.config.AwsSesConfig
import software.amazon.awssdk.core.SdkClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sesv2.SesV2Client

/**
 * Aws Ses IAM 을 관리하는 서비스 객체 입니다.
 *
 * @author 전지환
 * @since 1.0.0
 */
@Service
class SesCredentialService(
    private val awsSesConfig: AwsSesConfig
) : CredentialService {

    /**
     * aws ses 서비스를 사용할 수 있는 sdk 를 제공한다.
     *
     * @see AwsSesConfig ses IAM에 접근할 수 있는 설정
     */
    override fun getSdkClient(): SdkClient {

        return SesV2Client.builder()
            .credentialsProvider(
                getAwsCredentials(awsSesConfig.awsAccessKey, awsSesConfig.awsSecretKey)
            ).region(Region.of(awsSesConfig.awsRegion))
            .build()
    }
}