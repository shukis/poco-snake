package com.example.pavel.mygame.game

import com.example.pavel.mygame.framework.Screen
import com.example.pavel.mygame.framework.impl.AndroidGame


class StartGame : AndroidGame() {
    override val startScreen: Screen
        get() = LoadingScreen(this)
}