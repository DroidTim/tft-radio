package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import de.iu.tftradio.data.model.SongRequestList
import de.iu.tftradio.data.provider.ExampleRequestSongList
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class SongRequestRepository {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider<SongRequestList>()

    @Throws(NetworkErrorException::class)
    suspend fun getSongRequestList(clearCache: Boolean = false): SongRequestList {
        if (clearCache) radioMemoryProvider.clean()
        return radioMemoryProvider.retrieve() ?: radioMemoryProvider.cacheAndRetrieve(
            data = radioNetworkProvider.getRequestSongList().body() ?: throw NetworkErrorException()
        )
    }

    fun getSongRequestExample(): SongRequestList {
        return ExampleRequestSongList().songRequest
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongVote(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongVote(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongVoteOut(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongVoteOut(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongRequest(songTitle: String) {
        takeIf { radioNetworkProvider.postSongRequest(songTitle = songTitle).isSuccessful } ?: throw NetworkErrorException()
    }

}