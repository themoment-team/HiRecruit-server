package site.hirecruit.hr.domain.user.entity

enum class Role(
    val role: String,
    val title: String
){
    GUEST("ROLE_GUEST", "게스트"),  // 직장인 등록하지 않은 사용자
    WORKER("ROLE_WORKER", "직장인"),
    MENTOR("ROLE_MENTOR", "멘토"),
}
