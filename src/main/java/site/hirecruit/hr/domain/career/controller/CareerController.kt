package site.hirecruit.hr.domain.career.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.career.dto.CareerDto
import site.hirecruit.hr.domain.career.service.CareerService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import springfox.documentation.annotations.ApiIgnore

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
@RestController
@RequestMapping("/api/v1/career")
class CareerController(
    @Autowired
    val careerService: CareerService
) {

    @PostMapping("")
    fun createCareer(
        @RequestBody careerCreateRequestDto: CareerDto.CareerCreateRequestDto,
        @CurrentAuthUserInfo @ApiIgnore authUserInfo: AuthUserInfo
    ) : ResponseEntity<CareerDto.Info> {
        return ResponseEntity.ok().body(careerService.createCareer(careerCreateRequestDto, authUserInfo.githubId))
    }
}
