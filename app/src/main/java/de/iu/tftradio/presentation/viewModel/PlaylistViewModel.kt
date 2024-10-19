package de.iu.tftradio.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlaylistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val repository = PlaylistRepository()

    var errorDialog: Boolean by mutableStateOf(false)

    fun loadPlaylist(getExampleData: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (getExampleData) repository.getPlaylistExampleData() else repository.getPlaylist()
            }.onSuccess { playListDto ->
                _uiState.value = UiState.Success(playlistDto = playListDto)
            }.onFailure { error ->
                _uiState.value = UiState.Failure(exception = error)
            }
        }
    }

    fun setSongFavorite(songIdentifier: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var isSuccessful = false
            while (!isSuccessful) {
                runCatching {
                    repository.postSongFavorite(songIdentifier = songIdentifier)
                }.onSuccess {
                    isSuccessful = true
                    repository.getPlaylist(clearCache = true)
                }.onFailure {
                    delay(5000)
                }

            }
        }
    }

    fun setModeratorFeedback(moderatorIdentifier: String, stars: Int, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.postModeratorFeedback(moderatorFeedbackStars = ModeratorFeedbackStars(identifier = moderatorIdentifier, stars = stars))
            }.onSuccess {
                repository.getPlaylist(clearCache = true)
                onComplete.invoke()
            }.onFailure {
                errorDialog = true
                onComplete.invoke()
            }
        }
    }

}

sealed class UiState {
    data class Success(
        val playlistDto: PlaylistDto
    ) : UiState()

    data class Failure(val exception: Throwable) : UiState()
    data object Loading : UiState()
}