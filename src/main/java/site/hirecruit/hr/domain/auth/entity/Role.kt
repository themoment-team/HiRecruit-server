package site.hirecruit.hr.domain.auth.entity

enum class Role(
    val role: String,
    val title: String
){
    GUEST("ROLE_GUEST", "게스트"),  // 인증하지 않은 직장인
    CLIENT("ROLE_CLIENT", "사용자");
}
