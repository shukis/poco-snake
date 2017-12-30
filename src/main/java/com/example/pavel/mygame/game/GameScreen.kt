package com.example.pavel.mygame.game

import android.util.Log
import com.example.pavel.mygame.framework.Game
import com.example.pavel.mygame.framework.Input.TouchEvent
import com.example.pavel.mygame.framework.Pixmap
import com.example.pavel.mygame.framework.Screen

/**
 * Created by pavel on 14/11/2017.
 */
class GameScreen(game: Game) : Screen(game) {


    private var state = GameState.READY
    private var world: World = World()
    private var oldScore = 0
    private val scorePositions = arrayOf(200, 190, 176, 166)
    private var scorePixmapArray = arrayOf(Assets.zero, Assets.zero, Assets.zero, Assets.zero)

    internal enum class GameState {
        READY,
        RUNNING,
        PAUSED,
        GAMEOVER
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.input.touchEvents

        if (state == GameState.READY)
            updateReady(touchEvents)
        if (state == GameState.RUNNING)
            updateRunning(touchEvents, deltaTime)
        if (state == GameState.PAUSED)
            updateReady(touchEvents)
        if (state == GameState.GAMEOVER)
            updateGameOver(touchEvents)
    }

    private fun updateReady(touchEvents: List<TouchEvent>) {
        (0 until touchEvents.size).map { touchEvents[it] }.forEach {
            if (it.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(it, 0, 611, Assets.releaseSnake.width, Assets.releaseSnake.height)) {
                    if (Settings.soundEnabled)
                        Assets.click.play(1)
                    state = GameState.RUNNING
                    return
                }
                if (inBounds(it, 317, 27, Assets.sound.width + 100, Assets.sound.height + 100))
                    Settings.soundEnabled = !Settings.soundEnabled
            }
            if (it.type == TouchEvent.TOUCH_UP) {
                if (inBounds(it, 16, 30, Assets.leftIcon.width + 100, Assets.leftIcon.height + 100)) {
                    Settings.addScore(world.score)
                    Settings.save(game.fileIO)
                    game.setScreen(MainMenuScreen(game))
                }
            }
        }
    }

    private fun updateRunning(touchEvents: List<TouchEvent>, deltaTime: Float) {
        handleRunningTouchEvents(touchEvents)

        world.update(deltaTime)
        if (world.gameOver) {
            if (Settings.soundEnabled)
                Assets.bitten.play(1)
            state = GameState.GAMEOVER
        }
        if (oldScore != world.score) {
            oldScore = world.score
            if (Settings.soundEnabled)
                Assets.eat.play(1)
        }
    }

    private fun showScore(score: Int) {
        val g = game.graphics
        val scoreNumbers = game.score.showScore(score)
        for (i in 0 until scorePixmapArray.size) {
            scorePixmapArray[i] = getPixmapFromInt(scoreNumbers[i])
            g.drawPixmap(scorePixmapArray[i], scorePositions[i], 88)
        }
        g.drawPixmap(Assets.balance, 137, 66)
        g.drawPixmap(Assets.dot, 187, 96)
        g.drawPixmap(Assets.euro, 210, 88)
    }

    fun getPixmapFromInt(int: Int): Pixmap {
        return when (int) {
            1 -> Assets.one
            2 -> Assets.two
            3 -> Assets.three
            4 -> Assets.four
            5 -> Assets.five
            6 -> Assets.six
            7 -> Assets.seven
            8 -> Assets.eight
            9 -> Assets.nine
            else -> Assets.zero
        }
    }

    private fun handleRunningTouchEvents(touchEvents: List<TouchEvent>) {
        (0 until touchEvents.size).map { touchEvents[it] }.forEach {
            if (it.type == TouchEvent.TOUCH_DOWN) {
                if (state == GameState.RUNNING) {
                    when {
                        inBounds(it, 164, 615, Assets.downArrow.width, Assets.downArrow.height) ->
                            world.snake.turnDown()
                        inBounds(it, 164, 519, Assets.upArrow.width, Assets.upArrow.height) ->
                            world.snake.turnUp()
                        inBounds(it, 116, 567, Assets.leftArrow.width, Assets.leftArrow.height) ->
                            world.snake.turnLeft()
                        inBounds(it, 212, 567, Assets.rightArrow.width, Assets.rightArrow.height) ->
                            world.snake.turnRight()

                    }
                }
            }
            if (it.type == TouchEvent.TOUCH_UP) {
                if (inBounds(it, 321, 590, Assets.pause.width + 100, Assets.pause.height + 200)) {
                    if (state == GameState.RUNNING)
                        state = GameState.PAUSED
                }
                if (inBounds(it, 317, 27, Assets.sound.width + 100, Assets.sound.height + 100))
                    Settings.soundEnabled = !Settings.soundEnabled
            }
        }
    }


    private fun updateGameOver(touchEvents: List<TouchEvent>) {
        for (i in 0 until touchEvents.size) {
            val event = touchEvents[i]
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 600, Assets.tryAgain.width + 10, Assets.tryAgain.height + 10)) {
                    if (Settings.soundEnabled)
                        Assets.click.play(1)
                    Settings.addScore(world.score)
                    Settings.save(game.fileIO)
                    game.setScreen(GameScreen(game))
                    return
                }
                if (inBounds(event, 317, 27, Assets.sound.width + 100, Assets.sound.height + 100))
                    Settings.soundEnabled = !Settings.soundEnabled
                if (inBounds(event, 16, 30, Assets.closeButton.width, Assets.closeButton.height)) {
                    Settings.addScore(world.score)
                    Settings.save(game.fileIO)
                    game.setScreen(MainMenuScreen(game))
                }
            }
        }
    }


    override fun present(deltaTime: Float) {
        val g = game.graphics

        g.drawPixmap(Assets.background, 0, 0)

        drawWorld(world)
        if (state != GameState.READY) {
            showScore(oldScore)
        } else if (state == GameState.READY) {
            oldScore = 0
        }

        if (state == GameState.READY)
            drawReadyUI()
        if (state == GameState.RUNNING)
            drawRunningUI()
        if (state == GameState.PAUSED)
            drawReadyUI()
        if (state == GameState.GAMEOVER)
            drawGameOverUI()

    }

    private fun drawWorld(world: World) {
        val g = game.graphics

        val snake = world.snake
        val head = snake.parts[0]
        val coin = world.food


        val coinPixmap: Pixmap = Assets.coin
        var x = coin!!.x * 34
        var y = coin.y * 34
        g.drawPixmap(coinPixmap, x, y, if (state == GameState.RUNNING) null else World.ALPHA)

        val len = snake.parts.size
        for (i in 1 until len) {
            val part = snake.parts[i]
            x = part.x * 34
            y = part.y * 34
            g.drawPixmap(Assets.body, x, y, if (state == GameState.RUNNING) null else World.ALPHA)
        }

        var headPixmap: Pixmap = Assets.headRight

        if (snake.direction == Snake.UP) {
            headPixmap = Assets.headUp

        }
        if (snake.direction == Snake.LEFT) {
            headPixmap = Assets.headLeft

        }
        if (snake.direction == Snake.DOWN) {
            headPixmap = Assets.headDown

        }
        x = head.x * 34
        y = head.y * 34
        g.drawPixmap(headPixmap, x, y, if (state == GameState.RUNNING) null else World.ALPHA)

        if (world.redCoin != null) {
            x = world.redCoin!!.x * 34
            y = world.redCoin!!.y * 34
            g.drawPixmap(Assets.redCoin, x, y, if (state == GameState.RUNNING) null else World.ALPHA)
        }
        if (world.greenCoin != null) {
            x = world.greenCoin!!.x * 34
            y = world.greenCoin!!.y * 34
            g.drawPixmap(Assets.greenCoin, x, y, if (state == GameState.RUNNING) null else World.ALPHA)
        }
    }

    private fun drawReadyUI() {
        val g = game.graphics
        drawRunningUI(World.ALPHA)

        g.drawPixmap(if (state == GameState.READY) Assets.leftIcon else Assets.closeButton, 16, 32)
        g.drawPixmap(Assets.ready, 134, 314)
        g.drawPixmap(Assets.releaseSnake, 0, 611)
    }

    private fun drawRunningUI() {
        drawRunningUI(null)
    }

    private fun drawRunningUI(alpha: Int?) {
        val g = game.graphics
        if (state == GameState.RUNNING) {
            g.drawLine(511 * World.SIZE_FACTOR)
            g.drawPixmap(Assets.pause, 326, 601)
        }
        g.drawPixmap(Assets.upArrow, 164, 519, alpha)
        g.drawPixmap(Assets.downArrow, 164, 615, alpha)
        g.drawPixmap(Assets.leftArrow, 116, 567, alpha)
        g.drawPixmap(Assets.rightArrow, 212, 567, alpha)
        g.drawPixmap(Assets.sound, 327, 32, if (Settings.soundEnabled) null else World.ALPHA)
    }


    private fun drawGameOverUI() {
        val g = game.graphics
        drawRunningUI(World.ALPHA)

        g.drawPixmap(Assets.closeButton, 16, 32)
        g.drawPixmap(Assets.gameOver, 105, 314)
        g.drawPixmap(Assets.tryAgain, 0, 611)

    }

    override fun pause() {
        if (state == GameState.RUNNING)
            state = GameState.PAUSED

        if (world.gameOver) {
            Settings.addScore(world.score)
            Settings.save(game.fileIO)
        }
    }

    override fun resume() {

    }

    override fun dispose() {

    }

    override fun onBackPressed() {
        if (state == GameState.RUNNING) {
            pause()
        } else {
            state = GameState.RUNNING
        }
    }

}