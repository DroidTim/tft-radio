package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class PlaylistRepository {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider()

    @Throws(NetworkErrorException::class)
    suspend fun getPlaylist(): PlaylistDto {
        return radioMemoryProvider.retrieve() ?: radioMemoryProvider.cacheAndRetrieve(
            playlistDto = radioNetworkProvider.get().body() ?: throw NetworkErrorException()
        )
    }
}