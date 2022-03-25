package site.hirecruit.hr.domain.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hirecruit.hr.domain.worker.entity.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
}
