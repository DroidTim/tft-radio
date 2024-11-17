package de.iu.tftradio.presentation.viewModel.helper

internal sealed interface UiState<out T> {
    class Success<T>(
        val data: T
    ): UiState<T>
    class Failure<T>(val exception: Throwable) : UiState<T>
    data object Loading : UiState<Nothing>
}