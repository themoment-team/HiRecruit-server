package site.hirecruit.hr.global.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

private val log = KotlinLogging.logger {}

/**
 * Embedded redis config
 *
 * 프로필이 'local'일 떄 작동합니다.
 *
 * @author 정시원
 */
@Configuration
@Profile("local")
class EmbeddedRedisConfig(
    @Value("\${spring.redis.port}") val redisPort: Int
) {
    init {
        log.info("embedded redis will start port = '{}'", redisPort)
    }

    lateinit var redisServer: RedisServer

    @PostConstruct
    fun redisServer() {
        redisServer = RedisServer(redisPort)
        redisServer.start()
        log.info("embedded redis started successfully")
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
        log.info("embedded redis stopped successfully")
    }
}