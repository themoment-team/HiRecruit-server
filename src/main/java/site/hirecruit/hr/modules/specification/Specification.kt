package site.hirecruit.hr.modules.specification

interface Specification<T> {
    fun isSpecificatedBy(candidate: T)
}