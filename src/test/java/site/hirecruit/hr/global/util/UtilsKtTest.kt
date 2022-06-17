package site.hirecruit.hr.global.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UtilsKtTest{

    @Test
    @DisplayName("중복 없는 난수생성 테스트")
    fun randomDoesNotDup(){
        val num1 = randomNumberGenerator(6)
        val num2 = randomNumberGenerator(6)

        println("====== num1: $num1 ======= num2: $num2")

        assertThat(num1).isNotEqualTo(num2)
    }
}