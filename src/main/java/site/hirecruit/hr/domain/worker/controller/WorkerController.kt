package site.hirecruit.hr.domain.worker.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.service.AuthUserWorkerService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import springfox.documentation.annotations.ApiIgnore

/**
 * WorkerController
 *
 * @author 정시원
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/worker")
class WorkerController(
    private val authUserWorkerService: AuthUserWorkerService
) {

    @GetMapping("/me")
    private fun findMyWorkerInfo(
        @CurrentAuthUserInfo  @ApiIgnore
        authUserInfo: AuthUserInfo
    ): ResponseEntity<WorkerDto.Info> {
        val myWorkerInfo = authUserWorkerService.findWorkerByAuthUserInfo(authUserInfo)
        return ResponseEntity.ok(myWorkerInfo)
    }

    @PatchMapping("/me")
    private fun updateMyWorkerInfo(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,

        @RequestBody
        workerDto: WorkerDto.Update
    ): ResponseEntity<Unit>{
        authUserWorkerService.update(authUserInfo, workerDto)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }


}