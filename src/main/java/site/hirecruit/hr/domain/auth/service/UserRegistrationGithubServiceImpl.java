package site.hirecruit.hr.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;
import site.hirecruit.hr.domain.worker.repository.WorkerRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRegistrationGithubServiceImpl implements UserRegistrationService {

    private final WorkerRepository workerRepository;

    @Override
    public boolean isFirst(Long id) {
        return !workerRepository.existsByGithubId(id);
    }

    @Override
    public Optional<WorkerEntity> registration(OAuthAttributes oAuthAttributes) {
        final WorkerEntity workerEntity = oAuthAttributes
                .toEntity(WorkerEntity.Role.GUEST);
        final WorkerEntity savedWorkerEntity = workerRepository.save(workerEntity);
        return Optional.of(savedWorkerEntity);
    }

}
