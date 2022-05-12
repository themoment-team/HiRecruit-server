package site.hirecruit.hr.domain.mentoringApplication.entity

import site.hirecruit.hr.domain.mentee.entity.MenteeEntity
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import javax.persistence.*

@Entity(name = "mentoring_application")
class MentoringApplication(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentoring_id")
    val mentoringId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val menteeEntity: MenteeEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val workerEntity: WorkerEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val mentoringStatus: MentoringStatus

) {
    override fun toString(): String {
        return "MentoringApplication(mentoringId=$mentoringId, menteeEntity=$menteeEntity, workerEntity=$workerEntity, mentoringStatus=$mentoringStatus)"
    }
}