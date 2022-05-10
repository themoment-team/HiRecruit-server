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
     *
     * @param menteeInfo 등록 하고자 하는 멘티 정보.
     * @throws IllegalArgumentException 멘티가 이미 등록 돼 있을 때.
     * @see isMenteeEmailAlreadyRegistered
     */
    override fun registerMentee(menteeInfo: MenteeDto.MenteeRegistryFormatDto) : MenteeDto.MenteeInfoResponseDto{

        isMenteeEmailAlreadyRegistered(menteeInfo.email)

        val mentee = menteeRepository.save(menteeInfo.toEntity())
        return modelMapper.map(mentee, MenteeDto.MenteeInfoResponseDto::class.java)
    }

    /**
     * MenteeEmail로 Mentee가 이미 등록 돼 있는지 확인하는 메소드
     *
     * @throws IllegalArgumentException 멘티가 이미 등록 돼 있을 때.
     * @return false - Mentee가 등록되지 않았다면
     */
    private fun isMenteeEmailAlreadyRegistered(menteeEmail: String) : Boolean {
        val findMenteeByEmail = menteeRepository.findByEmail(menteeEmail)

        if (findMenteeByEmail != null) {
            throw IllegalArgumentException("등록하고자 하는 멘티 email: $menteeEmail 이 이미 존재 함.")
        }
        return false
    }


}