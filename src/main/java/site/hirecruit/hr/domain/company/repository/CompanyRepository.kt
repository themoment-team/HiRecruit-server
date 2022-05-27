package site.hirecruit.hr.domain.company.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.company.entity.CompanyEntity

/**
 * CompanyRepository
 *
 * @author 정시원
 * @since 1.0
 */
@Repository
interface CompanyRepository: JpaRepository<CompanyEntity, Long>{

}