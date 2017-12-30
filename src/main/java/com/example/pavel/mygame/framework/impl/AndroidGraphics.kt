package com.example.pavel.mygame.framework.impl

import android.content.res.AssetManager
import android.graphics.*
import com.example.pavel.mygame.game.World
import com.example.pavel.mygame.framework.Graphics
import com.example.pavel.mygame.framework.Pixmap
import java.io.IOException
import java.io.InputStream


/**
 * Created by pavel on 14/11/2017.
 */
class AndroidGraphics(internal var assets: AssetManager, frameBuffer: Bitmap) : Graphics {

    private var canvas: Canvas? = Canvas(frameBuffer)
    private var paint: Paint = Paint()
    private var srcRect = Rect()
    private var dstRect = Rect()


    override fun newPixmap(fileName: String, sizeX: Int, sizeY: Int): Pixmap {
        val nx = sizeX * World.SIZE_FACTOR
        val ny = sizeY * World.SIZE_FACTOR
        val config = Bitmap.Config.ARGB_8888

        val options = BitmapFactory.Options()

        options.inPreferredConfig = config

        var `in`: InputStream? = null
        val bitmap: Bitmap
        try {
            `in` = assets.open(fileName)
            bitmap = BitmapFactory.decodeStream(`in`, null, options)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'")
        } finally {
            try {
                `in`!!.close()
            } catch (e: IOException) {
            }
        }
        return if (nx != 0) {
            val newBitmap = Bitmap.createScaledBitmap(bitmap, nx, ny, false)
            AndroidPixmap(newBitmap)
        } else {
            AndroidPixmap(bitmap)
        }
    }

    override fun clear(color: Int) {
        canvas?.drawRGB(color and 0xff0000 shr 16, color and 0xff00 shr 8,
                color and 0xff)
    }


    override fun drawLine(y: Int) {
        paint.color = Color.rgb(255, 236,179)
        canvas?.drawLine(0.toFloat(), y.toFloat(), (375 * World.SIZE_FACTOR).toFloat(), y.toFloat(), paint)
    }


    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
                            srcWidth: Int, srcHeight: Int) {
        drawPixmap(pixmap, x, y, srcX, srcY, srcWidth, srcHeight, null)
    }


    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int) {
        drawPixmap(pixmap, x, y, null)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int, alpha: Int?) {
        srcRect.left = srcX
        srcRect.top = srcY
        srcRect.right = srcX + srcWidth - 1
        srcRect.bottom = srcY + srcHeight - 1

        dstRect.left = x * World.SIZE_FACTOR
        dstRect.top = y * World.SIZE_FACTOR
        dstRect.right = x * World.SIZE_FACTOR + srcWidth - 1
        dstRect.bottom = y * World.SIZE_FACTOR + srcHeight - 1

        alpha?.let {
            val paint = Paint()
            paint.alpha = it
            canvas?.drawBitmap((pixmap as AndroidPixmap).bitmap, srcRect, dstRect, paint)
            return
        }
        canvas?.drawBitmap((pixmap as AndroidPixmap).bitmap, srcRect, dstRect, null)

    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, alpha: Int?) {
        alpha?.let {
            val paint = Paint()
            paint.alpha = it
            canvas?.drawBitmap((pixmap as AndroidPixmap).bitmap, (x * World.SIZE_FACTOR).toFloat(),
                    (y * World.SIZE_FACTOR).toFloat(), paint)
            return
        }

        canvas?.drawBitmap((pixmap as AndroidPixmap).bitmap, (x * World.SIZE_FACTOR).toFloat(),
                (y * World.SIZE_FACTOR).toFloat(), null)
    }

    override fun disposeCanvas() {
        canvas = null
    }



}