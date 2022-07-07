package site.hirecruit.hr.batch.github_login_id.job

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Sort
import site.hirecruit.hr.batch.github_login_id.service.LockupGithubLoginIdService
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.user.repository.UserRepository


private val log = KotlinLogging.logger {  }

/**
 * 기존 사용자에 대한 github login Id(로그인시 사용되는 id, 혹은 주로 닉네임이라 부르는 것) 추가 배치 구성
 *
 * @author 정시원
 * @since 1.2
 */
//@Configuration
class AddGithubLoginIdJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val userRepository: UserRepository,
    private val tempUserRepository: TempUserRepository
) {

    private val lockupGithubLoginIdService = LockupGithubLoginIdService()

    /**
     * 기존 유저들의 github Login Id를 찾아서 저장하는 Job
     *
     * 해당 Job에는 하위 작업인 Step이 2개 존재한다.
     * 1. UserEntity에 githubLoginId저장
     * 2. TempUserEntity에 githubLoginId저장
     */
    @Bean
    fun addGithubLoginIdInUserJob(): Job =
        jobBuilderFactory.get("addGithubLoginIdJob")
            .start(addGithubLoginIdOnUserEntityStep())
            .build()

    /**
     * UserEntity에 githubLoginId저장하는 step
     */
    @Bean
    @JobScope
    fun addGithubLoginIdOnUserEntityStep(): Step = stepBuilderFactory["addGithubLoginIdOnUserEntityStep"]
            .chunk<UserEntity, UserEntity>(30)
            .reader(userEntityReader())
            .processor(ItemProcessor { userEntity ->
                val loginId = this.lockupGithubLoginIdService.findGithubLoginIdByGithubId(userEntity.githubId)
                userEntity.githubLoginId = loginId
                return@ItemProcessor userEntity
            })
            .writer(userEntityWriter())
            .build()

    @Bean
    fun userEntityReader(): RepositoryItemReader<UserEntity> =
        RepositoryItemReaderBuilder<UserEntity>()
            .repository(userRepository)
            .methodName("findAll")
            .sorts(mapOf("githubId" to Sort.Direction.DESC))
            .saveState(false)
            .build()

    @Bean
    fun userEntityWriter(): RepositoryItemWriter<UserEntity> = RepositoryItemWriterBuilder<UserEntity>()
        .repository(userRepository)
        .methodName("save")
        .build()


}