package com.example.pavel.mygame.framework

import java.util.ArrayList

/**
 * Created by pavel on 14/11/2017.
 */
class Pool<T>(private val factory: PoolObjectFactory<T>, private val maxSize: Int) {

    private val freeObjects: MutableList<T>

    interface PoolObjectFactory<T> {
        fun createObject(): T
    }

    init {
        this.freeObjects = ArrayList(maxSize)
    }

    fun newObject(): T? {
        var `object`: T? = if (freeObjects.size == 0)
            factory.createObject()
        else
            freeObjects.removeAt(freeObjects.size - 1)

        return `object`
    }

    fun free(`object`: T) {
        if (freeObjects.size < maxSize)
            freeObjects.add(`object`)
    }
}