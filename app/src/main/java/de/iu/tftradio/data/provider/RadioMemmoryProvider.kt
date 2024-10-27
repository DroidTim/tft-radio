package de.iu.tftradio.data.provider

internal class RadioMemoryProvider<T> {
    private var cache: T? = null

    fun cacheAndRetrieve(data: T): T {
        cache = data
        return data
    }

    fun retrieve(): T? {
        return cache
    }

    fun clean() {
        cache = null
    }
}