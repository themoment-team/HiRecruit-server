package site.hirecruit.hr.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@EnableRedisRepositories
class RedisRepositoryConfig(
    @Value("\${spring.redis.host}") val redisHost: String,
    @Value("\${spring.redis.port}") val redisPort: Int

) {

    /**
     * 비동기 이벤트 기반 고성능 네트워크 프레임워크 기반의 Redis 클라이언트, Lettuce를 사용한다.
     * @see <p> https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis.repositories.usage </p>
     */
    @Bean
    fun redisConnectionFactory() : RedisConnectionFactory{
        return LettuceConnectionFactory(redisHost, redisPort);
    }

    /**
     * RedisData의 쉬운 직렬화 역직렬화를 위한 RedisTemplate
     */
    @Bean
    fun redisTemplate(): RedisTemplate<ByteArray, ByteArray> {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
}
