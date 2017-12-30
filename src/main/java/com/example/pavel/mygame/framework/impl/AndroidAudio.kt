package com.example.pavel.mygame.framework.impl

import android.app.Activity
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.example.pavel.mygame.framework.Audio
import com.example.pavel.mygame.framework.Music
import com.example.pavel.mygame.framework.Sound
import java.io.IOException
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION
import android.os.Build.VERSION.SDK_INT



/**
 * Created by pavel on 14/11/2017.
 */
class AndroidAudio(activity: Activity) : Audio {
    internal var assets: AssetManager
    private var soundPool: SoundPool

    init {
        activity.volumeControlStream = AudioManager.STREAM_MUSIC
        this.assets = activity.assets
        this.soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                    .setMaxStreams(20)
                    .build()
        } else {
            SoundPool(20, AudioManager.STREAM_MUSIC, 0)
        }
    }

    override fun newMusic(filename: String): Music {
        try {
            val assetDescriptor = assets.openFd(filename)
            return AndroidMusic(assetDescriptor)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load music '$filename'")
        }

    }

    override fun newSound(filename: String): Sound {
        try {
            val assetDescriptor = assets.openFd(filename)
            val soundId = soundPool.load(assetDescriptor, 0)
            return AndroidSound(soundPool, soundId)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load sound '$filename'")
        }

    }
}