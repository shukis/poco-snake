package com.example.pavel.mygame.framework.impl

import android.util.Log
import android.view.View
import com.example.pavel.mygame.framework.Input.KeyEvent
import com.example.pavel.mygame.framework.Pool
import java.util.ArrayList

/**
 * Created by pavel on 14/11/2017.
 */
class KeyboardHandler(view: View) : View.OnKeyListener {
    private var pressedKeys = BooleanArray(128)
    private var keyEventPool: Pool<KeyEvent>
    private var keyEventsBuffer: MutableList<KeyEvent> = ArrayList()
    internal var keyEvents: MutableList<KeyEvent> = ArrayList()

    init {
        val factory = object : Pool.PoolObjectFactory<KeyEvent> {
            override fun createObject(): KeyEvent {
                return KeyEvent()
            }
        }
        keyEventPool = Pool(factory, 100)
        view.setOnKeyListener(this)
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }

    override fun onKey(v: View, keyCode: Int, event: android.view.KeyEvent): Boolean {
        if (event.action == android.view.KeyEvent.ACTION_MULTIPLE)
            return false

        synchronized(this) {
            val keyEvent = keyEventPool.newObject()
            keyEvent!!.keyCode = keyCode
            keyEvent.keyChar = event.unicodeChar.toChar()
            if(event.action == android.view.KeyEvent.KEYCODE_BACK) {

            }
            if (event.action == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeyEvent.KEY_DOWN
                if (keyCode in 0..127)
                    pressedKeys[keyCode] = true
            }
            if (event.action == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeyEvent.KEY_UP
                if (keyCode in 0..127)
                    pressedKeys[keyCode] = false
            }
            keyEventsBuffer.add(keyEvent)
        }
        return false
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return if (keyCode < 0 || keyCode > 127) false else pressedKeys[keyCode]
    }
}
