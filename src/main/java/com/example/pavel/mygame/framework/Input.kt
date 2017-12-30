package com.example.pavel.mygame.framework

/**
 * Created by pavel on 14/11/2017.
 */
interface Input {

    val accelX: Float

    val accelY: Float

    val accelZ: Float

    val keyEvents: List<KeyEvent>

    val touchEvents: List<TouchEvent>

    class KeyEvent {

        var type: Int = 0
        var keyCode: Int = 0
        var keyChar: Char = ' '

        override fun toString(): String {
            val builder = StringBuilder()
            if (type == KEY_DOWN)
                builder.append("key down, ")
            else
                builder.append("key up, ")
            builder.append(keyCode)
            builder.append(",")
            builder.append(keyChar)
            return builder.toString()
        }

        companion object {
            val KEY_DOWN = 0
            val KEY_UP = 1
        }
    }

    class TouchEvent {

        var type: Int = 0
        var x: Int = 0
        var y: Int = 0
        var pointer: Int = 0

        override fun toString(): String {
            val builder = StringBuilder()
            when (type) {
                TOUCH_DOWN -> builder.append("touch down, ")
                TOUCH_DRAGGED -> builder.append("touch dragged, ")
                else -> builder.append("touch up, ")
            }
            builder.append(pointer)
            builder.append(",")
            builder.append(x)
            builder.append(",")
            builder.append(y)
            return builder.toString()
        }

        companion object {
            val TOUCH_DOWN = 0
            val TOUCH_UP = 1
            val TOUCH_DRAGGED = 2
        }
    }

    fun isKeyPressed(keyCode: Int): Boolean

    fun isTouchDown(pointer: Int): Boolean

    fun getTouchX(pointer: Int): Int

    fun getTouchY(pointer: Int): Int
}