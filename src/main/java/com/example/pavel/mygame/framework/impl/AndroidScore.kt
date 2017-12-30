package com.example.pavel.mygame.framework.impl

import android.util.Log
import com.example.pavel.mygame.framework.Score

/**
 * Created by pavel on 18/11/2017.
 */
class AndroidScore : Score {


    private lateinit var digits: Array<Int>
    var index: Int = 0

    override fun showScore(score: Int): Array<Int> {
        digits = arrayOf(0, 0, 0, 0)
        printDigits(score)
        return digits
    }

    private fun printDigits(int: Int) {
        if (int / 10 > 0) {
            index++
            printDigits(int / 10)
        }
        digits[index] = (int % 10)
        if (index != 0) index--
    }


}