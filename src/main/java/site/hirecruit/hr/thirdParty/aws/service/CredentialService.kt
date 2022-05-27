package site.hirecruit.hr.thirdParty.aws.service

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.core.SdkClient

interface CredentialService {
    fun getSdkClient(): SdkClient

    /**
     * accessKey, secretKey 로 해당 IAM 의 자격을 얻는다.
     * default method로써 재정의를 강제하지 않는다.
     *
     * @see getSdkClient - credentials가 필요한 부분에 넣어준다.
     */
    fun getAwsCredentials(accessKey: String, secretKey: String) : AwsCredentialsProvider {
        return AwsCredentialsProvider {
            AwsBasicCredentials.create(accessKey, secretKey)
        }
    }
}