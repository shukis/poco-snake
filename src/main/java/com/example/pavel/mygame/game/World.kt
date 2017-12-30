package com.example.pavel.mygame.game

import java.util.*

/**
 * Created by pavel on 14/11/2017.
 */
class World {

    var snake: Snake = Snake()
    var food: Food? = null
    var redCoin: Position? = null
    var greenCoin: Position? = null

    var gameOver = false
    var score = 0
    var coinCounter = 0

    private var fields = Array(WORLD_WIDTH) { BooleanArray(WORLD_HEIGHT) }
    private var random = Random()
    private var tickTime = 0f

    private val newPosition: Position
        get() {
            checkFieldForFreeSpots()

            var newX = random.nextInt(WORLD_WIDTH)
            var newY = random.nextInt(WORLD_HEIGHT)
            while (true) {
                if (!fields[newX][newY])
                    break
                newX += 1
                if (newX >= WORLD_WIDTH) {
                    newX = 0
                    newY += 1
                    if (newY >= WORLD_HEIGHT) {
                        newY = 0
                    }
                }
            }
            return Position(newX, newY)
        }

    init {
        placeFood()
    }

    private fun placeFood() {
        val p = newPosition
        food = Food(p.x, p.y)
    }

    private fun checkFieldForFreeSpots() {
        for (x in 0 until WORLD_WIDTH) {
            for (y in 0 until WORLD_HEIGHT) {
                fields[x][y] = false
            }
        }

        val len = snake.parts.size
        (0 until len).map { snake.parts[it] }.forEach {
            fields[it.x][it.y] = true

        }

        if (food != null) {
            fields[food!!.x][food!!.y] = true
        }

        if (redCoin != null) {
            fields[redCoin!!.x][redCoin!!.y] = true
        }

        if (greenCoin != null) {
            fields[greenCoin!!.x][greenCoin!!.y] = true
        }
    }


    fun update(deltaTime: Float) {
        if (gameOver)
            return

        tickTime += deltaTime

        while (tickTime > tick) {
            tickTime -= tick
            snake.advance()
            if (snake.checkBitten()) {
                gameOver = true
                return
            }

            val head = snake.parts[0]
            if (head.x == food!!.x && head.y == food!!.y) {
                score += SCORE_INCREMENT
                snake.eat()
                if (snake.parts.size >= WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true
                    return
                } else {
                    coinCounter++
                    placeNextSetOfItems()
                }

                if (score % 10 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT
                }
            } else if (greenCoin != null && head.x == greenCoin!!.x && head.y == greenCoin!!.y) {
                score += SCORE_INCREMENT + 4
                snake.eatGreenCoin()
                greenCoin = null
                if (snake.parts.size >= WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true
                    return
                }
            } else if (redCoin != null && head.x == redCoin!!.x && head.y == redCoin!!.y) {
                score -= SCORE_INCREMENT
                snake.eatRedCoin()
                redCoin = null
                if (snake.parts.size >= WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true
                    return
                }
            }

        }
    }

    private fun placeNextSetOfItems() {
        placeFood()
        if (coinCounter % 4 == 0) {
            placeRedCoin()
        }
        if (coinCounter % 9 == 0) {
            placeGreenCoin()
        }
    }

    private fun placeGreenCoin() {
        greenCoin = newPosition
    }

    private fun placeRedCoin() {
        redCoin = newPosition
    }

    companion object {
        internal val WORLD_WIDTH = 11
        internal val WORLD_HEIGHT = 15
        internal val SCORE_INCREMENT = 3
        private val TICK_INITIAL = 0.5f
        internal val TICK_DECREMENT = 0.05f
        internal var tick = TICK_INITIAL
        val SIZE_FACTOR = 4
        val ALPHA = 50
    }

}
