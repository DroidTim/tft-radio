package de.iu.tftradio.presentation.viewModel.helper

internal sealed class UiState {
    data class Success<T>(
        val data: T
    ): UiState()
    data class Failure(val exception: Throwable) : UiState()
    data object Loading : UiState()
}