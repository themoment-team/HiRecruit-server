package site.hirecruit.hr.domain.mentoringRequestDetails.entity

import site.hirecruit.hr.domain.mentoringApplication.entity.MentoringApplication
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

/**
 * 멘토링 신청 디테일 엔티티
 *
 * @author 전지환
 * @since 1.0.0
 */
@Entity(name = "mentoring_request_details")
class MentoringRequestDetailsEntity(

    @Id
    val mentoringApplicationId: Long? = null,

    @MapsId
    @OneToOne
    @JoinColumn(name = "mentoring_application_id")
    val mentoringApplication: MentoringApplication,

    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val content: String

){
    override fun toString(): String {
        return "MentoringRequestDetailsEntity(mentoringApplicationId=$mentoringApplicationId, mentoringApplication=$mentoringApplication, title='$title', content='$content')"
    }
}