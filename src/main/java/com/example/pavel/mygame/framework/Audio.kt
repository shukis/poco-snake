package com.example.pavel.mygame.framework

/**
 * Created by pavel on 14/11/2017.
 */
interface Audio {
    fun newMusic(filename: String): Music

    fun newSound(filename: String): Sound
}