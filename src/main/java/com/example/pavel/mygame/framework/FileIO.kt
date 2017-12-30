package com.example.pavel.mygame.framework

import android.content.SharedPreferences
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by pavel on 14/11/2017.
 */
interface FileIO {

    val preferences: SharedPreferences
    @Throws(IOException::class)
    fun readAsset(fileName: String): InputStream

    @Throws(IOException::class)
    fun readFile(fileName: String): InputStream

    @Throws(IOException::class)
    fun writeFile(fileName: String): OutputStream
}