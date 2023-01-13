package site.hirecruit.hr.global.util

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
enum class YnType(
    val value: String,
    val code: Int,
    val asBoolean: Boolean
) {
    Y("Y", 1, true),
    N("N", 0, false),
    ;
}
