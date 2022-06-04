package site.hirecruit.hr.global.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import java.io.BufferedReader
import java.io.InputStreamReader
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
@Profile("local", "token-auth")
class EmbeddedRedisConfig(
    @Value("\${spring.redis.port}") private val defaultRedisPort: Int
) {

    private lateinit var redisServer: RedisServer

    @PostConstruct
    private fun redisServer() {
        val port: Int = if (isRedisRunning()) findAvailablePort() else defaultRedisPort
        log.info("embedded redis will start port = '{}'", defaultRedisPort)
        redisServer = RedisServer(port)
        redisServer.start()
        log.info("embedded redis started successfully")
    }

    @PreDestroy
    private fun stopRedis() {
        redisServer.stop()
        log.info("embedded redis stopped successfully")
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private fun isRedisRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(defaultRedisPort))
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    private fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalStateException("Not Found Available port: 10000 ~ 65535")
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN|grep $port"
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     */
    private fun isRunning(process: Process): Boolean {
        var line: String?
        val pidInfo = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream)).use { input ->
            while (input.readLine().also { line = it } != null) {
                pidInfo.append(line)
            }
        }
        return pidInfo.isNotEmpty()
    }

}