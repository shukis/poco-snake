package com.example.pavel.mygame.game

import com.example.pavel.mygame.framework.Game
import com.example.pavel.mygame.framework.Input.TouchEvent
import com.example.pavel.mygame.framework.Screen

/**
 * Created by pavel on 14/11/2017.
 */
class MainMenuScreen(game: Game) : Screen(game) {

    override fun onBackPressed() {

    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.touchEvents

        for (i in 0 until touchEvents.size) {
            val event = touchEvents[i]
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 158, 385, Assets.startButton.width, Assets.startButton.height)) {
                    game.setScreen(GameScreen(game))
                    if (Settings.soundEnabled) Assets.click.play(1)
                    return
                }
                if (inBounds(event, 138, 596, Assets.highscore.width, Assets.highscore.height)) {
                    game.setScreen(HighScoreScreen(game))
                    if (Settings.soundEnabled) Assets.click.play(1)
                    return
                }
                if (inBounds(event, 317, 27, Assets.sound.width + 100, Assets.sound.height + 100)) {
                    Settings.soundEnabled = !Settings.soundEnabled
                    return
                }
                if (inBounds(event, 16, 30, Assets.closeButton.width + 100, Assets.closeButton.height + 100)) {
                    game.closeApp()
                }
            }
        }
    }


    override fun present(deltaTime: Float) {
        val g = game.graphics

        g.drawPixmap(Assets.background, 0, 0)
        g.drawPixmap(Assets.logo, 109, 100)
        g.drawPixmap(Assets.snakeBubble, 87, 234)
        g.drawPixmap(Assets.closeButton, 16, 30)
        g.drawPixmap(Assets.startButton, 163, 390)
        g.drawPixmap(Assets.highscore, 143, 601)
        g.drawPixmap(Assets.sound, 327, 32, if (Settings.soundEnabled) null else World.ALPHA)
    }

    override fun pause() {
        Settings.save(game.fileIO)
    }

    override fun resume() {

    }

    override fun dispose() {

    }
}
