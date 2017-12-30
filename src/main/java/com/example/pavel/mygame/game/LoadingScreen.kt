package com.example.pavel.mygame.game

import com.example.pavel.mygame.framework.Game
import com.example.pavel.mygame.framework.Screen

/**
 * Created by pavel on 14/11/2017.
 */
class LoadingScreen(game: Game) : Screen(game) {

    override fun update(deltaTime: Float) {
        val g = game.graphics
        Assets.background = g.newPixmap("bg1.png", 375, 667)
        Assets.logo = g.newPixmap("pocosnake.png", 158, 31)
        Assets.snakeBubble = g.newPixmap("snakebubble.png", 200, 200)
        Assets.closeButton = g.newPixmap("close.png", 24, 24)
        Assets.highscore = g.newPixmap("highScore.png", 89, 18)
        Assets.upArrow = g.newPixmap("upArrow.png", 49, 49)
        Assets.downArrow = g.newPixmap("downArrow.png", 49, 49)
        Assets.leftArrow = g.newPixmap("leftArrow.png", 49, 49)
        Assets.rightArrow = g.newPixmap("rightArrow.png", 49, 49)
        Assets.pause = g.newPixmap("pause.png", 12, 24)
        Assets.sound = g.newPixmap("sound.png", 24, 24)
        Assets.releaseSnake = g.newPixmap("release.png", 375, 56)
        Assets.ready = g.newPixmap("ready.png", 110, 33)
        Assets.leftIcon = g.newPixmap("icnLeft.png", 24, 24)
        Assets.startButton = g.newPixmap("start.png", 49, 49)
        Assets.headUp = g.newPixmap("headUp.png", 32, 34)
        Assets.headLeft = g.newPixmap("headLeft.png", 34, 32)
        Assets.headDown = g.newPixmap("headDown.png", 32, 34)
        Assets.headRight = g.newPixmap("headRight.png", 34, 32)
        Assets.body = g.newPixmap("body.png", 32, 32)
        Assets.coin = g.newPixmap("coin.png", 32, 32)
        Assets.zero = g.newPixmap("0.png", 10, 10)
        Assets.one = g.newPixmap("1.png", 5, 10)
        Assets.two = g.newPixmap("2.png", 8, 10)
        Assets.three = g.newPixmap("3.png", 8, 10)
        Assets.four = g.newPixmap("4.png", 8, 10)
        Assets.five = g.newPixmap("5.png", 8, 10)
        Assets.six = g.newPixmap("6.png", 8, 10)
        Assets.seven = g.newPixmap("7.png", 8, 10)
        Assets.eight = g.newPixmap("8.png", 8, 10)
        Assets.nine = g.newPixmap("9.png", 8, 10)
        Assets.dot = g.newPixmap("dot.png", 2, 2)
        Assets.euro = g.newPixmap("euro.png", 8, 10)
        Assets.redCoin = g.newPixmap("redCoin.png", 32, 32)
        Assets.greenCoin = g.newPixmap("greenCoin.png", 32, 32)
        Assets.gameOver = g.newPixmap("gameOver.png", 160, 28)
        Assets.tryAgain = g.newPixmap("tryAgain.png", 375, 56)
        Assets.balance = g.newPixmap("balance.png", 103, 12)



        Assets.click = game.audio.newSound("click.ogg")
        Assets.eat = game.audio.newSound("eat.ogg")
        Assets.bitten = game.audio.newSound("bitten.ogg")
        Settings.load(game.fileIO)
        game.setScreen(MainMenuScreen(game))
    }

    override fun present(deltaTime: Float) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

    override fun onBackPressed() {

    }

}