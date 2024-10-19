package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.provider.ExampleData
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class PlaylistRepository {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider()

    @Throws(NetworkErrorException::class)
    suspend fun getPlaylist(clearCache: Boolean = false): PlaylistDto {
        if (clearCache) radioMemoryProvider.clean()
        return radioMemoryProvider.retrieve() ?: radioMemoryProvider.cacheAndRetrieve(
            playlistDto = radioNetworkProvider.getPlaylist().body() ?: throw NetworkErrorException()
        )
    }

    fun getPlaylistExampleData(): PlaylistDto {
        return ExampleData().playlist
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongFavorite(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongFavorite(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

    @Throws(NetworkErrorException::class)
    suspend fun postModeratorFeedback(moderatorFeedbackStars: ModeratorFeedbackStars) {
        takeIf { radioNetworkProvider.postModeratorFeedback(moderatorFeedbackStars = moderatorFeedbackStars).isSuccessful } ?: throw NetworkErrorException()
    }
}