package site.hirecruit.hr.domain.career.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.hirecruit.hr.domain.career.CareerEntity

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
interface CareerRepository : JpaRepository<CareerEntity, Long>{
}
