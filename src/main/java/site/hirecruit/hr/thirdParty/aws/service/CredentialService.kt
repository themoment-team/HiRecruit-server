package site.hirecruit.hr.thirdParty.aws.service

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.core.SdkClient

interface CredentialService {
    fun getSdkClient(): SdkClient
    fun getAwsCredentials(accessKey: String, secretKey: String) : AwsCredentialsProvider
}