package de.iu.tftradio.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.model.SongRequestList
import de.iu.tftradio.data.repository.SongRequestRepository
import de.iu.tftradio.presentation.viewModel.helper.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class SongRequestViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<SongRequestList>>(UiState.Loading)
    val uiState: StateFlow<UiState<SongRequestList>> = _uiState

    private lateinit var repository : SongRequestRepository

    fun initialize(sharedPreferences: SharedPreferences) {
        repository = SongRequestRepository(sharedPreferences = sharedPreferences)
    }

    var hasError: Boolean = false

    fun loadSongRequests(getExampleData: Boolean = false, clearCache: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (getExampleData) repository.getSongRequestExample() else repository.getSongRequestList(clearCache = clearCache)
            }.onSuccess { songRequestList ->
                _uiState.value = UiState.Success(data = songRequestList)
            }.onFailure { error ->
                _uiState.value = UiState.Failure(exception = error)
            }
        }
    }

    fun getSongVoted(songIdentifier: String): Boolean {
        return repository.getVotedSongs().any { songIdentifier == it }
    }

    fun postSongVote(songIdentifier: String, isVote: Boolean) {
        var isSuccessful = false
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                while (!isSuccessful) {
                    if (isVote) {
                        repository.postSongVote(songIdentifier = songIdentifier)
                    } else {
                        repository.postSongVoteOut(songIdentifier = songIdentifier)
                    }
                }
                loadSongRequests(clearCache = true)
            }.onSuccess {
                isSuccessful = true
                if (!isVote) {
                    repository.setVote(songIdentifier = songIdentifier)
                } else {
                    repository.removeVote(songIdentifier = songIdentifier)
                }
            }.onFailure {
                delay(5000)
            }
        }
    }

    fun postSongRequest(songTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.postSongRequest(songTitle = songTitle)
            }.onSuccess {
                loadSongRequests(clearCache = true)
            }.onFailure {
                hasError = true
            }
        }
    }

}