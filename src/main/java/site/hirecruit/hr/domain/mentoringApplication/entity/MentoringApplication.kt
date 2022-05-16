package site.hirecruit.hr.domain.mentoringApplication.entity

import site.hirecruit.hr.domain.mentee.entity.MenteeEntity
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import javax.persistence.*

/**
 * 멘토링 신청 엔티티
 *
 * @see MentoringRequestDetailsEntity 멘토링 신청 디테일 엔티티와 1:1 식별 관계
 * @author 전지환
 * @since 1.0.0
 */
@Entity(name = "mentoring_application")
class MentoringApplication(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentoring_application_id")
    val mentoringApplicationId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val menteeEntity: MenteeEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val workerEntity: WorkerEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val mentoringStatus: MentoringStatus

) {
    override fun toString(): String {
        return "MentoringApplication(mentoringId=$mentoringApplicationId, menteeEntity=$menteeEntity, workerEntity=$workerEntity, mentoringStatus=$mentoringStatus)"
    }
}