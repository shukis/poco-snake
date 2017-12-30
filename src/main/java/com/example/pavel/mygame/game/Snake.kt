package com.example.pavel.mygame.game

import java.util.*

/**
 * Created by pavel on 14/11/2017.
 */
class Snake {

    var parts: MutableList<SnakePart> = ArrayList()
    var direction: Int = 0

    companion object {
        val UP = 0
        val LEFT = 1
        val DOWN = 2
        val RIGHT = 3
    }

    init {
        direction = UP
        parts.add(SnakePart(5, 6))
        parts.add(SnakePart(5, 7))
        parts.add(SnakePart(5, 8))
    }

    fun turnRight() {
        if (direction != LEFT)
            direction = RIGHT
    }

    fun turnLeft() {
        if (direction != RIGHT)
            direction = LEFT
    }

    fun turnUp() {
        if (direction != DOWN)
            direction = UP
    }

    fun turnDown() {
        if (direction != UP)
            direction = DOWN
    }

    fun eat() {
        val end = parts[parts.size - 1]
        parts.add(SnakePart(end.x, end.y))
    }

    fun eatGreenCoin() {
        for (i in 0 until 2) {
            parts.remove(parts[parts.size - 1])
        }
    }

    fun eatRedCoin() {
        for (i in 0 until 2) {
            eat()
        }
    }


    fun advance() {
        val head = parts[0]

        val len = parts.size - 1
        for (i in len downTo 1) {
            val before = parts[i - 1]
            val part = parts[i]
            part.x = before.x
            part.y = before.y
        }

        if (direction == UP)
            head.y -= 1
        if (direction == LEFT)
            head.x -= 1
        if (direction == DOWN)
            head.y += 1
        if (direction == RIGHT)
            head.x += 1

        if (head.x < 0)
            head.x = World.WORLD_WIDTH - 1
        if (head.x > World.WORLD_WIDTH - 1)
            head.x = 0
        if (head.y < 0)
            head.y = World.WORLD_HEIGHT - 1
        if (head.y > World.WORLD_HEIGHT - 1)
            head.y = 0
    }

    fun checkBitten(): Boolean {
        val len = parts.size
        val head = parts[0]
        return (1 until len)
                .map { parts[it] }
                .any { it.x == head.x && it.y == head.y }
    }

}
