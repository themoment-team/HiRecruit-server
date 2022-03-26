package site.hirecruit.hr.domain.auth.service;

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.auth.dto.SessionUser;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

import java.util.Optional;

/**
 * 유저의 회원가입을 담당하는 서비스
 *
 * @author 정시원
 * @see UserRegistrationGithubServiceImpl
 */
interface UserRegistrationService {

    /**
     * 서비스를 처음 이용하는지 검증하는 메서드
     *
     * @param id - OAuth인증 식별자 (유일함) ex. github_id
     */
    boolean isFirst(Long id);

    /**
     * 회원가입 시 role type은 {@link WorkerEntity.Role#GUEST}로 저장된다. <br>
     * 그 후 추가적인 정보를 입력해 프로필을 업데이트 하면 그 때 {@link WorkerEntity.Role#CLIENT}로 변경
     *
     * @param oAuthAttributes OAuth 사용자의 첫 회원가입 정보가 담겨있는 메서드
     * @return 회원가입이 완료된 WorkerEntity
     */
    Optional<WorkerEntity> registration(OAuthAttributes oAuthAttributes);

}
