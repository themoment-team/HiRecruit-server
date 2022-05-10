package site.hirecruit.hr.domain.mentee.service

import site.hirecruit.hr.domain.mentee.dto.MenteeDto
import site.hirecruit.hr.domain.mentee.repository.MenteeRepository

/**
 * @author 전지환
 * @since 1.0.0
 */
class MenteeServiceImpl(
    private val menteeRepository: MenteeRepository
) : MenteeService {

    /**
     * 멘티로 등록하는 비즈니스 로직
     */
    override fun registerMentee(menteeInfo: MenteeDto.MenteeRegistryFormatDto) {
        menteeRepository.save(menteeInfo.toEntity())
    }
}