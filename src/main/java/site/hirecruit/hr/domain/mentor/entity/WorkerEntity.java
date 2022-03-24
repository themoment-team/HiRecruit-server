package site.hirecruit.hr.domain.mentor.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WorkerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    private String email;

    private String name;

    private String profileUri;

    private String company;

    private String location;

    private String introduction;



}
