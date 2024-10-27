package de.iu.tftradio.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SongRequest(val title: String, var favorites: Int = 0)

internal class SongRequestViewModel : ViewModel() {
    private val _songRequestList = MutableStateFlow<List<SongRequest>>(emptyList())
    val songRequestList: StateFlow<List<SongRequest>> = _songRequestList

    fun loadSongRequest() {

    }
    // Methode, um einen neuen Songwunsch hinzuzufügen
    fun addSongRequest(newRequest: String) {
        if (newRequest.isNotBlank()) {
            _songRequestList.value += SongRequest(newRequest)
        }
    }

    // Methode, um einen Song als Favorit zu markieren
    fun favoriteSong(request: SongRequest) {
        _songRequestList.value = _songRequestList.value.map {
            if (it == request) it.copy(favorites = it.favorites + 1) else it
        }.sortedByDescending { it.favorites }
    }
}