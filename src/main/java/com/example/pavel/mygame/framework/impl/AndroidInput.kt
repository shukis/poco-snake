package com.example.pavel.mygame.framework.impl

import android.content.Context
import android.view.View
import com.example.pavel.mygame.framework.Input
import com.example.pavel.mygame.framework.Input.TouchEvent
import com.example.pavel.mygame.framework.Input.KeyEvent

/**
 * Created by pavel on 14/11/2017.
 */
class AndroidInput(context: Context, view: View, scaleX: Float, scaleY: Float) : Input {
    private var accelHandler: AccelerometerHandler = AccelerometerHandler(context)
    private var keyHandler: KeyboardHandler = KeyboardHandler(view)
    private var touchHandler: TouchHandler = MultiTouchHandler(view, scaleX, scaleY)

    override val accelX: Float
        get() = accelHandler.accelX

    override val accelY: Float
        get() = accelHandler.accelY

    override val accelZ: Float
        get() = accelHandler.accelZ

    override val touchEvents: List<TouchEvent>
        get() = touchHandler.getTouchEvents()

    override val keyEvents: List<KeyEvent>
        get() = keyHandler.keyEvents

    override fun isKeyPressed(keyCode: Int): Boolean {
        return keyHandler.isKeyPressed(keyCode)
    }

    override fun isTouchDown(pointer: Int): Boolean {
        return touchHandler.isTouchDown(pointer)
    }

    override fun getTouchX(pointer: Int): Int {
        return touchHandler.getTouchX(pointer)
    }

    override fun getTouchY(pointer: Int): Int {
        return touchHandler.getTouchY(pointer)
    }
}
