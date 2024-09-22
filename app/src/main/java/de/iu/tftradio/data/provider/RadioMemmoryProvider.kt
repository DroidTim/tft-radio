package de.iu.tftradio.data.provider

import de.iu.tftradio.data.model.PlaylistDto

internal class RadioMemoryProvider {
    private var cache: PlaylistDto? = null

    fun cacheAndRetrieve(playlistDto: PlaylistDto): PlaylistDto {
        cache = playlistDto
        return playlistDto
    }

    fun retrieve(): PlaylistDto? {
        return cache
    }

    fun clean() {
        cache = null
    }
}