package site.hirecruit.hr.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurerSupport() {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 30
        executor.setQueueCapacity(50)
        executor.setThreadNamePrefix("AWS-SERVICE-ASYNC-")
        executor.initialize()

        return executor
    }
}