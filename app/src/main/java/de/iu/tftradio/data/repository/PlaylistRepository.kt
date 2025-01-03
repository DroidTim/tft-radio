package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import android.content.SharedPreferences
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.model.PlaylistVotes
import de.iu.tftradio.data.provider.ExampleDataPlaylist
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class PlaylistRepository(
    val sharedPreferences: SharedPreferences
) {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider<PlaylistDto>()
    private val playlistVotes = PlaylistVotes(sharedPreferences = sharedPreferences)

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

    fun getVotedSongs(): List<String> {
        return playlistVotes.getVotes()
    }

    fun setVote(songIdentifier: String) {
        playlistVotes.setVote(songIdentifier = songIdentifier)
    }

    fun removeVote(songIdentifier: String) {
        playlistVotes.removeVote(songIdentifier = songIdentifier)
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongFavorite(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongFavorite(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

    @Throws(NetworkErrorException::class)
    suspend fun postSongFavoriteOut(songIdentifier: String) {
        takeIf { radioNetworkProvider.postSongFavoriteOut(songIdentifier = songIdentifier).isSuccessful } ?: throw NetworkErrorException()
    }

}