package site.hirecruit.hr.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

import java.io.Serializable;

/**
 * 로그인 시 session에 저장될 정보를 담고있는 객체
 *
 * @author 정시원
 */
@Getter @Builder
@Component
@AllArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUser implements Serializable {

    private final Long userId;
    private final String name;
    private final String email;
    private final String profileUri;
    private final WorkerEntity.Role role;

    public SessionUser(WorkerEntity worker){
        this.userId = worker.getWorkerId();
        this.name = worker.getName();
        this.email = worker.getEmail();
        this.profileUri = worker.getProfileUri();
        this.role = worker.getRole();
    }

}
