package com.example.pavel.mygame.framework.impl

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.os.Environment
import android.preference.PreferenceManager
import com.example.pavel.mygame.framework.FileIO
import java.io.*

/**
 * Created by pavel on 14/11/2017.
 */
class AndroidFileIO(internal var context: Context) : FileIO {
    internal var assets: AssetManager = context.assets
    private var externalStoragePath: String = Environment.getExternalStorageDirectory()
            .absolutePath + File.separator

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    @Throws(IOException::class)
    override fun readAsset(fileName: String): InputStream {
        return assets.open(fileName)
    }

    @Throws(IOException::class)
    override fun readFile(fileName: String): InputStream {
        return FileInputStream(externalStoragePath + fileName)
    }

    @Throws(IOException::class)
    override fun writeFile(fileName: String): OutputStream {
        return FileOutputStream(externalStoragePath + fileName)
    }
}