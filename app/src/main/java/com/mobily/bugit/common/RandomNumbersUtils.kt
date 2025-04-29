package com.mobily.bugit.common

object RandomNumbersUtils {
    const val FROM_DEFAULT = 1
    const val TO_DEFAULT = 1000000
    fun generateRandomNumber(from:Int,to:Int) = (from..to).random()
    fun generateRandomNumberAsString(from:Int,to:Int) = generateRandomNumber(from,to).toString()
}