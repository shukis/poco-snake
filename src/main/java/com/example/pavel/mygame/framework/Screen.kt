package com.example.pavel.mygame.framework

import android.util.Log
import com.example.pavel.mygame.game.World


/**
 * Created by pavel on 14/11/2017.
 */
abstract class Screen(protected val game: Game) {

    abstract fun update(deltaTime: Float)

    abstract fun present(deltaTime: Float)

    abstract fun pause()

    abstract fun resume()

    abstract fun dispose()

    abstract fun onBackPressed()

    fun inBounds(event: Input.TouchEvent, x: Int, y: Int, width: Int, height: Int): Boolean {
        val xStartPoint = (x-10) * World.SIZE_FACTOR
        val xEndPoint = (x+10) * World.SIZE_FACTOR + width
        val yStartPoint = (y-10) * World.SIZE_FACTOR
        val yEndPoint = (y+10) * World.SIZE_FACTOR + height
        return (event.x in xStartPoint..xEndPoint && event.y in yStartPoint..yEndPoint)
    }

}