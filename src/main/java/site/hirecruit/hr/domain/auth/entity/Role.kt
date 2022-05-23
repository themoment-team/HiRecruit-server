package site.hirecruit.hr.domain.auth.entity

enum class Role(
    val role: String,
    val title: String
){
    GUEST("ROLE_GUEST", "게스트"),  // 인증하지 않은 직장인
    CLIENT("ROLE_CLIENT", "사용자"),
    UNAUTHENTICATED_EMAIL("ROLE_UNAUTHENTICATED_EMAIL", "email 인증되지 않은 사용자")
}
