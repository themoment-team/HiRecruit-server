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
import site.hirecruit.hr.domain.worker.service.WorkerService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo

/**
 *
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/worker")
class WorkerController(
    private val workerService: WorkerService
) {

    @GetMapping("/me")
    private fun findMyWorkerInfo(@CurrentAuthUserInfo authUserInfo: AuthUserInfo): ResponseEntity<WorkerDto.Info> {
        val myWorkerInfo = workerService.findWorkerByAuthUserInfo(authUserInfo)
        return ResponseEntity.ok(myWorkerInfo)
    }

    @PatchMapping("/me")
    private fun updateMyWorkerInfo(
        @CurrentAuthUserInfo authUserInfo: AuthUserInfo,
        @RequestBody workerDto: WorkerDto.Update
    ): ResponseEntity<Unit>{
        workerService.update(authUserInfo, workerDto)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }


}