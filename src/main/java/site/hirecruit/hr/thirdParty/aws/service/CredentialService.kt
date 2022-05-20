package site.hirecruit.hr.thirdParty.aws.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.config.AwsSnsConfig
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

/**
 * aws sns IAM 의 accessKey, secretKey를 Provider에 제공함으로써 서비스 접근 권한을 얻는다.
 *
 * @since 1.0.0
 * @author 전지환
 */
@Service
class CredentialService(private val awsSnsConfig: AwsSnsConfig) {

    fun getAwsCredentials(accessKey: String, secretKey: String): AwsCredentialsProvider {
        return AwsCredentialsProvider {
            AwsBasicCredentials.create(accessKey, secretKey)
        }
    }

    fun getSnsClient(): SnsClient {
        return SnsClient.builder()
            .credentialsProvider(
                getAwsCredentials(awsSnsConfig.awsAccessKey, awsSnsConfig.awsSecretKey)
            ).region(Region.AP_NORTHEAST_2)
            .build()
    }
}