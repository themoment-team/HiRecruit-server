package site.hirecruit.hr.global.util

import java.util.*

fun uuidGenerator(email: String): String {
    val charset = Charsets.UTF_8
    val byteArray = email.toByteArray(charset)
    val uuid = UUID.nameUUIDFromBytes(byteArray)

    fun getUUIDInfoDetails() {
        println("payloadData = $email")
        println("charset = $charset")
        println("byteArray = ${byteArray.contentToString()}")
        println("uuid = $uuid")
    }

    return uuid.toString()
}