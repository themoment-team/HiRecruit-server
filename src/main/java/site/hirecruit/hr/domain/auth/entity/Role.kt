package site.hirecruit.hr.domain.auth.entity

enum class Role(
    val role: String,
    val title: String
){
    GUEST("ROLE_GUEST", "게스트"),  // 직장인 등록하지 않은 사용자
    WORKER("ROLE_WORKER", "직장인"),
    MENTOR("ROLE_MENTOR", "멘토"),
    CLIENT("ROLE_CLIENT", "사용자"),
    UNAUTHENTICATED_EMAIL("ROLE_UNAUTHENTICATED_EMAIL", "email 인증되지 않은 사용자") // 하위 호환성을 위해 남겨놓음
}
