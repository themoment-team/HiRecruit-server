package site.hirecruit.hr.domain.anonymous.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity;

/**
 * @version 1.0.0
 * @author 전지환
 */
@Repository
public interface AnonymousRepository extends JpaRepository<AnonymousEntity, Long> {

}
