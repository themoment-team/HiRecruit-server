package site.hirecruit.hr.global.util

import mu.KotlinLogging
import java.util.*

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