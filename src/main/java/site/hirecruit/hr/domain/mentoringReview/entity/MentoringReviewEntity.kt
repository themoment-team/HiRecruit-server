package site.hirecruit.hr.domain.mentoringReview.entity

import site.hirecruit.hr.domain.mentoringApplication.entity.MentoringApplication
import javax.persistence.*

@Entity(name = "mentoring_review")
class MentoringReviewEntity(

    @Id
    val mentoringApplicationId: Long,

    @MapsId
    @OneToOne
    @JoinColumn(name = "mentoring_application_id")
    val mentoringApplication: MentoringApplication,

    @Column(name = "score")
    val score: Int = 4,

    @Column(name = "review")
    val review: String?

)