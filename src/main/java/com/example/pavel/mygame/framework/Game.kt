package com.example.pavel.mygame.framework

/**
 * Created by pavel on 14/11/2017.
 */
interface Game {
    val input: Input

    val fileIO: FileIO

    val graphics: Graphics

    val audio: Audio

    val currentScreen: Screen

    val startScreen: Screen

    val score: Score

    fun setScreen(screen: Screen)

    fun closeApp()
}