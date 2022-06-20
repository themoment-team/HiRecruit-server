package site.hirecruit.hr.domain.mentor.dto

class MentorDto {
    /**
     * v1.3 기준으로 mentor 등업에 필요한 인증 체계 정보를 담은 Dto 입니다.
     *
     * @param workerId 등업 대상 worker
     * @param verificationCode 사용자가 입력할 인증 번호
     */
    class MentorVerifyVerificationMethodRequestDto(
        val workerId: Long,
        val verificationCode: String
    )
}