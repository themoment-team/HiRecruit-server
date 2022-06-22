package site.hirecruit.hr.domain.worker.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import kotlin.random.Random

internal class WorkerLockupServiceImplTest {

    private val workerRepository: WorkerRepository = mockk()
    private val workerLockupService = WorkerLockupServiceImpl(workerRepository)

    @Test @DisplayName("findWorkerInfoDtoByWorkerId")
    fun findWorkerInfoDtoByWorkerIdTest() {
        // given
        val workerEntity = WorkerEntity(
            workerId = Random.nextLong(),
            user = mockk(relaxed = true),
            company = mockk(relaxed = true)
        )
        val workerInfoDto = WorkerDto.Info(
            workerId = workerEntity.workerId!!,
            name = workerEntity.user.name,
            email = workerEntity.user.email,
            profileImgUri = workerEntity.user.profileImgUri,
            introduction = workerEntity.introduction,
            giveLink = workerEntity.giveLink,
            devYear = workerEntity.devYear,
            position = workerEntity.position,
            userType = Role.WORKER,
            companyInfoDto = CompanyDto.Info(
                companyId = workerEntity.company.companyId!!,
                name = workerEntity.company.name,
                location = workerEntity.company.location,
                homepageUri = workerEntity.company.homepageUri,
                companyImgUri = workerEntity.company.companyImgUri
            )
        )

        every { workerRepository.findWorkerInfoDtoByWorkerId(workerEntity.workerId!!) } answers {workerInfoDto}

        // when
        val returnValue = workerLockupService.findByWorkerId(workerEntity.workerId!!)

        // then
        verify(exactly = 1) { workerRepository.findWorkerInfoDtoByWorkerId(workerEntity.workerId!!) }
        assertEquals(workerInfoDto, returnValue)
    }

    @Test @DisplayName("findWorkerInfoDtoByWorkerId 만약 ")
    fun findWorkerInfoDtoByWorkerIdExceptionTest() {
        // given
        val workerId = Random.nextLong(Long.MAX_VALUE / 2, Long.MAX_VALUE)

        every { workerRepository.findWorkerInfoDtoByWorkerId(any()) } answers {null}

        // when then
        assertThrows(IllegalArgumentException::class.java) {
            workerLockupService.findByWorkerId(workerId)
        }
    }

    @Test @DisplayName("findWorkerInfoDtoBy")
    fun findWorkerInfoDtoByCompanyId(){
        val companyId = Random.nextLong()
        val workerInfoDtoList: List<WorkerDto.Info> = emptyList()

        every { workerRepository.findWorkerInfoDtoByCompanyId(companyId) } answers {workerInfoDtoList}

        val returnValue = workerLockupService.findByCompanyId(companyId)

        verify(exactly = 1) { workerRepository.findWorkerInfoDtoByCompanyId(companyId) }
        assertEquals(workerInfoDtoList, returnValue)
    }

    @Test @DisplayName("findWorkerInfoDtoByCompanyId")
    fun findAllWorkerInfoDto() {
        val workerInfoDtoList: List<WorkerDto.Info> = emptyList()

        every { workerRepository.findAllWorkerInfoDto() } answers { workerInfoDtoList }

        val returnValue = workerLockupService.findAll()

        verify(exactly = 1) { workerRepository.findAllWorkerInfoDto() }
        assertEquals(workerInfoDtoList, returnValue)
    }
}