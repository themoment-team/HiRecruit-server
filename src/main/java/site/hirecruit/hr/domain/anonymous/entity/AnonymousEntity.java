package site.hirecruit.hr.domain.anonymous.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @version 1.0.0
 * @author 전지환
 */
@Table
@Entity(name = "anonymous")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnonymousEntity {

    @Id
    @Column(name = "anonymous_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anonymousId;

    @NotNull
    @Column(name = "anonymous_uuid")
    private String anonymousUUID;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "email_certified")
    private boolean emailCertified;

}
