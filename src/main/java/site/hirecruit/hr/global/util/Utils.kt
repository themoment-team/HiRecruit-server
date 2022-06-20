package site.hirecruit.hr.global.util

import mu.KotlinLogging
import java.security.SecureRandom
import java.util.*
import kotlin.collections.HashSet

private val log = KotlinLogging.logger {}

fun generateUUID(email: String): String {
    val charset = Charsets.UTF_8
    val byteArray = email.toByteArray(charset)
    val uuid = UUID.nameUUIDFromBytes(byteArray)

    fun getUUIDInfoDetails() {
        log.info { "payloadData = $email" }
        log.info { "charset = $charset" }
        log.info { "byteArray = ${byteArray.contentToString()}" }
        log.info { "uuid = $uuid" }
    }

    return uuid.toString()
}

/**
 * 중복이 없는 난수를 발생시키는 함수입니다.
 *
 * @param length 난수 길이를 고정합니다.
 * @return result - 생성된 중복 없는 난수
 */
fun randomNumberGenerator(length: Int): String{
    val randomPod: HashSet<String> = hashSetOf()
    val random = SecureRandom()

    val result = StringBuilder()

    for (i: Int in 1..length){
        result.append(random.nextInt(9))
    }

    // retry
    if (randomPod.contains(result.toString())){
        randomNumberGenerator(length)
    }

    return result.toString()
}