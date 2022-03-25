package site.hirecruit.hr.domain.auth.dto;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

import java.io.Serializable;

@Getter
@Component
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
