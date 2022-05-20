package site.hirecruit.hr.thirdParty.aws.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.config.AwsSnsConfig
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

@Service
class CredentialService(val awsSnsConfig: AwsSnsConfig) {

    fun getAwsCredentials(accessKey: String, secretKey: String): AwsCredentialsProvider {
        return AwsCredentialsProvider {
            AwsBasicCredentials.create(accessKey, secretKey)
        }
    }

    fun getSnsClient(): SnsClient {
        return SnsClient.builder()
            .credentialsProvider(
                getAwsCredentials(awsSnsConfig.awsAccessKey, awsSnsConfig.awsSecretKey)
            ).region(Region.of(awsSnsConfig.awsRegion))
            .build()
    }
}