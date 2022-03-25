package site.hirecruit.hr.domain.worker.entity;


import lombok.*;

import javax.management.relation.Role;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WorkerEntity {

    @Getter
    @RequiredArgsConstructor
    public enum Role{
        GUEST("ROLE_GUEST", "게스트"), // 인증이 안된 직장인
        CLIENT("ROLE_CLIENT", "사용자"); // 인증된 직장인

        private final String role;
        private final String title;

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    private String email;

    private String name;

    private String profileUri;

    private String company;

    private String location;

    private String introduction;

    private Role role;

    public void updateRole(Role role){
        assert role != null : "The 'role' field cannot be null";
        this.role = role;
    }

}
