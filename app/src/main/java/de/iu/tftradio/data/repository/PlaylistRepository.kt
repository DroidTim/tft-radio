package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.provider.ExampleDataPlaylist
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class PlaylistRepository {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider<PlaylistDto>()

    @Throws(NetworkErrorException::class)
    suspend fun getPlaylist(clearCache: Boolean = false): PlaylistDto {
        if (clearCache) radioMemoryProvider.clean()
        return radioMemoryProvider.retrieve() ?: radioMemoryProvider.cacheAndRetrieve(
            data = radioNetworkProvider.getPlaylist().body() ?: throw NetworkErrorException()
        )
    }

    fun getPlaylistExampleData(): PlaylistDto {
        return ExampleDataPlaylist().playlist
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongFavorite(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongFavorite(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

}