package com.example.pavel.mygame.framework

/**
 * Created by pavel on 14/11/2017.
 */
interface Music {

    val isPlaying: Boolean

    val isStopped: Boolean

    var isLooping: Boolean

    fun play()

    fun stop()

    fun pause()

    fun setVolume(volume: Float)

    fun dispose()
}