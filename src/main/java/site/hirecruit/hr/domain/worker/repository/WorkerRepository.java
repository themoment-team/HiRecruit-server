package site.hirecruit.hr.domain.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
    boolean existsByGithubId(Long id);

    Optional<WorkerEntity> findByGithubId(long githubId);
}
