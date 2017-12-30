package com.example.pavel.mygame.game

import android.util.Log
import com.example.pavel.mygame.framework.Game
import com.example.pavel.mygame.framework.Input.TouchEvent
import com.example.pavel.mygame.framework.Pixmap
import com.example.pavel.mygame.framework.Screen

/**
 * Created by pavel on 14/11/2017.
 */
class HighScoreScreen(game: Game) : Screen(game) {
    private var scorePixmapArray = arrayOf(Assets.zero, Assets.zero, Assets.zero, Assets.zero)
    private val xScorePositions = arrayOf(200, 190, 176, 166)
    private val yScorePositions = arrayOf(200, 270, 340, 410, 480)

    override fun onBackPressed() {
        game.setScreen(MainMenuScreen(game))
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.touchEvents

        val len = touchEvents.size
        (0 until len).map { touchEvents[it] }.filter { it.type == TouchEvent.TOUCH_UP }.forEach {
            if (inBounds(it, 16, 30, Assets.leftIcon.width + 100, Assets.leftIcon.height + 100)) {
                game.setScreen(MainMenuScreen(game))
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.graphics
        g.drawPixmap(Assets.background, 0, 0, null)
        g.drawPixmap(Assets.leftIcon, 16, 30)
        g.drawPixmap(g.newPixmap("highScore.png", 120, 25), 125, 100)
        for (i in 0..4) {
            g.drawPixmap(GameScreen(game).getPixmapFromInt(i + 1), 146, yScorePositions[i])
            g.drawPixmap(Assets.dot, 157, yScorePositions[i] + 8)
            showScore(Settings.highscores[i], yScorePositions[i])
        }
    }

    private fun showScore(score: Int, y: Int) {
        val g = game.graphics
        val scoreNumbers = game.score.showScore(score)
        for (x in 0 until scorePixmapArray.size) {
            scorePixmapArray[x] = GameScreen(game).getPixmapFromInt(scoreNumbers[x])
            g.drawPixmap(scorePixmapArray[x], xScorePositions[x], y)
        }

        g.drawPixmap(Assets.dot, 187, y + 8)
        g.drawPixmap(Assets.euro, 210, y)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

}