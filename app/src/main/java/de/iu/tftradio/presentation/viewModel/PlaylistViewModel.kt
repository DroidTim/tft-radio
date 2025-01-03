package de.iu.tftradio.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.repository.PlaylistRepository
import de.iu.tftradio.presentation.viewModel.helper.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlaylistViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PlaylistDto>>(UiState.Loading)
    val uiState: StateFlow<UiState<PlaylistDto>> = _uiState

    private lateinit var repository : PlaylistRepository

    fun initialize(sharedPreferences: SharedPreferences) {
        repository = PlaylistRepository(sharedPreferences = sharedPreferences)
    }

    fun loadPlaylist(getExampleData: Boolean = false, clearCache: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (getExampleData) repository.getPlaylistExampleData() else repository.getPlaylist(clearCache = clearCache)
            }.onSuccess { playListDto ->
                _uiState.value = UiState.Success(data = playListDto)
            }.onFailure { error ->
                _uiState.value = UiState.Failure(exception = error)
            }
        }
    }

    fun getSongVoted(songIdentifier: String): Boolean {
        return repository.getVotedSongs().any { songIdentifier == it }
    }

    fun setSongFavorite(songIdentifier: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            var isSuccessful = false
            while (!isSuccessful) {
                runCatching {
                    if (!isFavorite) {
                        repository.postSongFavorite(songIdentifier = songIdentifier)
                    } else {
                        repository.postSongFavoriteOut(songIdentifier = songIdentifier)
                    }
                }.onSuccess {
                    isSuccessful = true
                    if (!isFavorite) {
                        repository.setVote(songIdentifier = songIdentifier)
                    } else {
                        repository.removeVote(songIdentifier = songIdentifier)
                    }
                    // Don't reload the list. If you want this you have to remember the scrolling position. This gives conflicts with the scrolling to the played title.
                }.onFailure {
                    delay(5000)
                }
            }
        }
    }

}