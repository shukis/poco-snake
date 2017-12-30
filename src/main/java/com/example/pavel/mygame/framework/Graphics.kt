package com.example.pavel.mygame.framework

/**
 * Created by pavel on 14/11/2017.
 */
interface Graphics {

    fun newPixmap(fileName: String, sizeX: Int, sizeY: Int): Pixmap

    fun clear(color: Int)

    fun drawLine(y: Int)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
                   srcWidth: Int, srcHeight: Int)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
                   srcWidth: Int, srcHeight: Int, alpha: Int?)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, alpha: Int?)

    fun disposeCanvas()
}