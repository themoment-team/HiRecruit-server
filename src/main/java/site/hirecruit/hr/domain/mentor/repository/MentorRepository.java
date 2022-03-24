package site.hirecruit.hr.domain.mentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hirecruit.hr.domain.mentor.entity.WorkerEntity;

public interface MentorRepository extends JpaRepository<WorkerEntity, Long> {
}
