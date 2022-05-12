package site.hirecruit.hr.domain.mentoringDetails.entity

import site.hirecruit.hr.domain.mentoringApplication.entity.MentoringApplication
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity(name = "mentoring_details")
class MentoringDetailsEntity(
    @Id
    val mentoringId: Long? = null,

    @MapsId
    @OneToOne
    @JoinColumn(name = "mentoring_id")
    val mentoringApplication: MentoringApplication,

    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val content: String
)