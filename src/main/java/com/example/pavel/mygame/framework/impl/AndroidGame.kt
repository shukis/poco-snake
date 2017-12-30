package com.example.pavel.mygame.framework.impl

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PowerManager
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.example.pavel.mygame.R
import com.example.pavel.mygame.framework.*
import com.example.pavel.mygame.game.MainMenuScreen
import com.example.pavel.mygame.game.World


/**
 * Created by pavel on 14/11/2017.
 */
abstract class AndroidGame : Activity(), Game {


    private lateinit var renderView: AndroidFastRenderView

    override lateinit var graphics: Graphics

    override lateinit var audio: Audio

    override lateinit var input: Input

    override lateinit var fileIO: FileIO

    override lateinit var currentScreen: Screen

    override lateinit var score: Score

    private lateinit var wakeLock: PowerManager.WakeLock

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString(EXTRA)
            val dialog = CustomDialog(getString(R.string.welcome, value), this)
            dialog.show()

        }

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val frameBufferWidth = 375 * World.SIZE_FACTOR
        val frameBufferHeight = 667 * World.SIZE_FACTOR
        val frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Bitmap.Config.ARGB_8888)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels
        val deviceHeight = displayMetrics.heightPixels

        val scaleX = frameBufferWidth.toFloat() / deviceWidth
        val scaleY = frameBufferHeight.toFloat() / deviceHeight

        renderView = AndroidFastRenderView(this, frameBuffer)
        graphics = AndroidGraphics(assets, frameBuffer)
        fileIO = AndroidFileIO(this)
        audio = AndroidAudio(this)
        input = AndroidInput(this, renderView, scaleX, scaleY)
        score = AndroidScore()

        currentScreen = startScreen
        setContentView(renderView)

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "pocoSnake")
    }

    public override fun onResume() {
        super.onResume()
        wakeLock.acquire()
        currentScreen.resume()
        renderView.resume()
    }

    public override fun onPause() {
        super.onPause()
        wakeLock.release()
        renderView.pause()
        currentScreen.pause()

        if (isFinishing)
            currentScreen.dispose()
    }

    override fun setScreen(screen: Screen) {
        this.currentScreen.pause()
        screen.resume()
        screen.update(0F)
        this.currentScreen = screen
    }

    override fun onBackPressed() {
        if (currentScreen is MainMenuScreen) super.onBackPressed()
        else currentScreen.onBackPressed()
    }

    override fun closeApp() {
        this.finish()
    }

    companion object {
        val EXTRA = AndroidGame.javaClass.name + "EXTRA"
    }
}