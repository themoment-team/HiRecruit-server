package site.hirecruit.hr.domain.mentee.service

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.mentee.dto.MenteeDto
import site.hirecruit.hr.domain.mentee.repository.MenteeRepository

/**
 * @author 전지환
 * @since 1.0.0
 */
@Service
class MenteeServiceImpl(
    private val menteeRepository: MenteeRepository,
    private val modelMapper: ModelMapper
) : MenteeService {

    /**
     * 멘티로 등록하는 비즈니스 로직
     */
    override fun registerMentee(menteeInfo: MenteeDto.MenteeRegistryFormatDto) : MenteeDto.MenteeInfoResponseDto{
        val mentee = menteeRepository.save(menteeInfo.toEntity())
        return modelMapper.map(mentee, MenteeDto.MenteeInfoResponseDto::class.java)
    }
}