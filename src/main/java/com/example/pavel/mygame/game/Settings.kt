package com.example.pavel.mygame.game

import com.example.pavel.mygame.framework.FileIO
import java.io.*

/**
 * Created by pavel on 14/11/2017.
 */
object Settings {
    var soundEnabled = true
    var highscores = intArrayOf(0, 0, 0, 0, 0)

    fun load(files: FileIO) {
        var inbr: BufferedReader? = null
        try {
            inbr = BufferedReader(InputStreamReader(
                    files.readFile(".pocoGame")))
            soundEnabled = java.lang.Boolean.parseBoolean(inbr.readLine())
            for (i in 0..4) {
                highscores[i] = Integer.parseInt(inbr.readLine())
            }
        } catch (e: IOException) {

        } finally {
            try {
                if (inbr != null)
                    inbr.close()
            } catch (e: IOException) {
            }

        }
    }

    fun save(files: FileIO) {
        var outbr: BufferedWriter? = null
        try {
            outbr = BufferedWriter(OutputStreamWriter(
                    files.writeFile(".pocoGame")))
            outbr.write(java.lang.Boolean.toString(soundEnabled))
            outbr.write("\n")
            for (i in 0..4) {
                outbr.write(Integer.toString(highscores[i]))
                outbr.write("\n")
            }

        } catch (e: IOException) {
        } finally {
            try {
                if (outbr != null)
                    outbr.close()
            } catch (e: IOException) {
            }

        }
    }

    fun addScore(score: Int) {
        for (i in 0..4) {
            if (highscores[i] == score) break
            if (highscores[i] < score) {
                for (j in 4 downTo i + 1)
                    highscores[j] = highscores[j - 1]
                highscores[i] = score
                break
            }
        }
    }
}
