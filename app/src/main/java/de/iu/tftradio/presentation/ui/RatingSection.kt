package de.iu.tftradio.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.iu.tftradio.data.model.ModeratorFeedback
import de.iu.tftradio.presentation.ErrorScreen
import de.iu.tftradio.presentation.viewModel.ModeratorFeedbackViewModel
import de.iu.tftradio.presentation.viewModel.helper.UiState

@Composable
internal fun RatingsSection(viewModel: ModeratorFeedbackViewModel) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.loadModeratorFeedbackList(getExampleData = true)
    }

    when(val state = viewModel.uiState.collectAsState().value) {
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            val moderatorFeedbackList = state.data as List<*>
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(moderatorFeedbackList) {
                    RatingCard(moderatorFeedback = it as ModeratorFeedback)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is UiState.Failure -> {
            ErrorScreen(exception = state.exception) {
                viewModel.loadModeratorFeedbackList(getExampleData = true)
            }
        }
    }
}

@Composable
private fun RatingCard(moderatorFeedback: ModeratorFeedback) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ModeratorStars(stars = moderatorFeedback.rating, onClick = {})
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${moderatorFeedback.rating} Sterne)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = moderatorFeedback.comment,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}