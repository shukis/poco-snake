package com.example.pavel.mygame.framework.impl

import android.media.SoundPool
import com.example.pavel.mygame.framework.Sound

/**
 * Created by pavel on 14/11/2017.
 */
class AndroidSound(private var soundPool: SoundPool, private var soundId: Int) : Sound {

    override fun play(volume: Int) {
        soundPool.play(soundId, volume.toFloat(), volume.toFloat(), 0, 0, 1f)
    }

    override fun dispose() {
        soundPool.unload(soundId)
    }

}