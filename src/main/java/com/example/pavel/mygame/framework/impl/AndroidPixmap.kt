package com.example.pavel.mygame.framework.impl

import android.graphics.Bitmap
import com.example.pavel.mygame.framework.Pixmap

/**
 * Created by pavel on 14/11/2017.
 */
class AndroidPixmap(internal var bitmap: Bitmap) : Pixmap {

    override val width: Int
        get() = bitmap.width

    override val height: Int
        get() = bitmap.height

    override fun dispose() {
        bitmap.recycle()
    }
}
