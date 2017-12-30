package com.example.pavel.mygame.framework.impl

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.SurfaceHolder
import android.view.SurfaceView

@SuppressLint("ViewConstructor")

/**
 * Created by pavel on 14/11/2017.
 */
class AndroidFastRenderView(internal var game: AndroidGame, private var framebuffer: Bitmap) : SurfaceView(game), Runnable {
    private var renderThread: Thread? = null
    internal var holder: SurfaceHolder = getHolder()
    @Volatile private var running = false

    fun resume() {
        running = true
        renderThread = Thread(this)
        renderThread!!.start()
    }

    override fun run() {
        val dstRect = Rect()
        var startTime = System.nanoTime()
        while (running) {
            if (!holder.surface.isValid)
                continue

            val deltaTime = (System.nanoTime() - startTime) / 1000000000.0f
            startTime = System.nanoTime()


            game.currentScreen.update(deltaTime)
            game.currentScreen.present(deltaTime)

            val canvas = holder.lockCanvas()
            canvas.getClipBounds(dstRect)
            canvas.drawBitmap(framebuffer, null, dstRect, null)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        running = false
        while (true) {
            try {
                renderThread!!.join()
                break
            } catch (e: InterruptedException) {
            }

        }
    }
}