package site.hirecruit.hr.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.auth.dto.SessionUser;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;
import site.hirecruit.hr.domain.worker.repository.WorkerRepository;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
class UserAuthServiceImpl implements UserAuthService {

    private final WorkerRepository workerRepository;
    private final HttpSession httpSession;

    /**
     * DB에 유저가 있는지 조회 후 성공적으로 완료되면 login을 진행한다. <br>
     * login 성공시 {@link SessionUser} 객체가 "user" 라는 이름으로 세션에 저장됩니다.
     */
    @Override
    public SessionUser login(OAuthAttributes oAuthAttributes) {

        final WorkerEntity user = workerRepository.findByGithubId(oAuthAttributes.getId())
                .orElseThrow(() -> new OAuth2AuthenticationException("User not found"));

        final SessionUser sessionUser = SessionUser.builder()
                .userId(user.getWorkerId())
                .name(user.getName())
                .email(user.getEmail())
                .profileUri(user.getProfileUri())
                .role(user.getRole())
                .build();

        httpSession.setAttribute("user", sessionUser);

        return sessionUser;
    }
}
