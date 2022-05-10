package site.hirecruit.hr.domain.mentee.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.mentee.dto.MenteeDto

@Service
interface MenteeService {
    fun registerMentee(menteeInfo: MenteeDto.MenteeRegistFormatDto)
}