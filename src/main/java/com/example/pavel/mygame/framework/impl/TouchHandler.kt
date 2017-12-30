package com.example.pavel.mygame.framework.impl

import android.view.View
import com.example.pavel.mygame.framework.Input.TouchEvent
/**
 * Created by pavel on 14/11/2017.
 */
interface TouchHandler : View.OnTouchListener {

    fun getTouchEvents(): List<TouchEvent>

    fun isTouchDown(pointer: Int): Boolean

    fun getTouchX(pointer: Int): Int

    fun getTouchY(pointer: Int): Int
}