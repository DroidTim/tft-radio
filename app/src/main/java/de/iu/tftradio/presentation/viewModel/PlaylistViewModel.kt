package de.iu.tftradio.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.repository.PlaylistRepository
import de.iu.tftradio.presentation.viewModel.helper.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlaylistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val repository = PlaylistRepository()

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

    fun setSongFavorite(songIdentifier: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var isSuccessful = false
            while (!isSuccessful) {
                runCatching {
                    repository.postSongFavorite(songIdentifier = songIdentifier)
                }.onSuccess {
                    isSuccessful = true
                    _uiState.value = UiState.Success(data = repository.getPlaylist(clearCache = true))
                }.onFailure {
                    delay(5000)
                }
            }
        }
    }

}