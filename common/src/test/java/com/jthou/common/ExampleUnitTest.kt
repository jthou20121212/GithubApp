package com.jthou.common

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testBoolean() {
        val result = false.yes {
           1
        }.otherwise {
           2
        }

        Assert.assertEquals(result, 2)
    }

}
