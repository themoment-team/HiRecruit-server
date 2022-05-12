package site.hirecruit.hr.domain.mentoringApplication.entity

/**
 * 멘토링 신청 이후의 상태를 나타내는 enum class
 *
 * @author 전지환
 * @since 1.0.0
 */
enum class MentoringStatus {
    PENDING, BROWSE, APPROVED, REJECT
}