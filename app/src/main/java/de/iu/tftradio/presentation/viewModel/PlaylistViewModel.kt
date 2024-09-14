package de.iu.tftradio.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlaylistViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val repository = PlaylistRepository()

    fun loadPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.getPlaylist()
            }.onSuccess {
                _uiState.value = UiState.Success(playlistDto = it)
            }.onFailure {
                _uiState.value = UiState.Failure(exception = it)
            }
        }
    }

}

sealed class UiState {
    data class Success(
        val playlistDto: PlaylistDto
    ) : UiState()
    data class Failure(val exception: Throwable) : UiState()
    data object Loading: UiState()
}