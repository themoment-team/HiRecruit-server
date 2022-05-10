package site.hirecruit.hr.domain.mentee.dto

import site.hirecruit.hr.domain.mentee.entity.MenteeEntity
import site.hirecruit.hr.global.util.uuidGenerator

class MenteeDto {

    /**
     * 멘티를 등록할 때 사용하는 Dto
     * @author 전지환
     */
    class MenteeRegistFormatDto(
        private val name: String,
        private val email: String,
    ){

        /**
         * MenteeEntity를 생성하는 팩토리 메소드
         * 확장함수를 이용해 UUID를 만들어주는 역할과 책임을 분리 시킴.
         *
         * @see uuidGenerator
         */
        fun toEntity(): MenteeEntity {
            return MenteeEntity(
                menteeId = null,
                menteeUUID = uuidGenerator(email),
                name = this.name,
                email = this.email,
                emailCertified = false
            )
        }

    }
}