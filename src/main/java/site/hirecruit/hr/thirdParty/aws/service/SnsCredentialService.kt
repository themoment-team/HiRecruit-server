package site.hirecruit.hr.thirdParty.aws.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.config.AwsSnsConfig
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

/**
 * aws sns IAM 의 accessKey, secretKey를 Provider에 제공함으로써 서비스 접근 권한을 얻는다.
 *
 * @since 1.0.0
 * @author 전지환
 */
@Service
class SnsCredentialService(private val awsSnsConfig: AwsSnsConfig) : CredentialService{

    /**
     * aws sns 서비스의 permission이 있는 IAM의 자격을 얻어 properties에 알맞는 region으로 SnsClient를 제공해준다.
     *
     * @see AwsSnsConfig sns전용 IAM에 대한 설정
     */
    override fun getSdkClient(): SnsClient {

        return SnsClient.builder()
            .credentialsProvider(
                getAwsCredentials(awsSnsConfig.awsAccessKey, awsSnsConfig.awsSecretKey)
            ).region(Region.of(awsSnsConfig.awsRegion))
            .build()
    }

}