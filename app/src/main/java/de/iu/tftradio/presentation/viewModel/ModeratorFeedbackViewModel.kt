package de.iu.tftradio.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.repository.ModeratorRepository
import de.iu.tftradio.presentation.viewModel.helper.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class ModeratorFeedbackViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val repository = ModeratorRepository()

    var errorDialog: Boolean by mutableStateOf(false)

    fun loadModeratorFeedbackList(getExampleData: Boolean = false, clearCache: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (getExampleData) repository.getModeratorFavoriteList() else repository.getModeratorFeedbackList(clearCache = clearCache)
            }.onSuccess { moderatorFeedbackList ->
                _uiState.value = UiState.Success(data = moderatorFeedbackList)
            }.onFailure { error ->
                _uiState.value = UiState.Failure(exception = error)
            }
        }
    }

    fun setModeratorFeedback(moderatorIdentifier: String, stars: Int, comment: String, onComplete: (result: Result<Unit>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching {
                repository.postModeratorFeedback(moderatorFeedbackStars = ModeratorFeedbackStars(identifier = moderatorIdentifier, stars = stars, comment = comment))
            }
            onComplete.invoke(result)
        }
    }

}