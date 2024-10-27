package de.iu.tftradio.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.iu.tftradio.presentation.viewModel.SongRequest
import de.iu.tftradio.presentation.viewModel.SongRequestViewModel
import kotlinx.coroutines.launch

@Composable
internal fun SongRequest(modifier: Modifier = Modifier, songRequestViewModel: SongRequestViewModel) {
    val songRequestList by songRequestViewModel.songRequestList.collectAsState()
    var currentSongRequest by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(16.dp).fillMaxSize()) {
        Text("Songwunsch eingeben:", style = MaterialTheme.typography.titleMedium)

        TextField(
            value = currentSongRequest,
            onValueChange = { currentSongRequest = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray),
            placeholder = { Text("Songwunsch eingeben") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            songRequestViewModel.addSongRequest(currentSongRequest.text)
                            currentSongRequest = TextFieldValue("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Senden",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Songwünsche:", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Liste der Songwünsche anzeigen und nach Favoriten sortieren
        SongRequestList(songRequests = songRequestList, onFavoriteClick = { song ->
            songRequestViewModel.favoriteSong(song)
        })
    }
}


@Composable
private fun SongRequestList(songRequests: List<SongRequest>, onFavoriteClick: (SongRequest) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        songRequests.forEach { songRequest ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF0F0F0))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${songRequest.title} (${songRequest.favorites} Favoriten)",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = { onFavoriteClick(songRequest) }) {
                    if (songRequest.favorites > 0) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorisieren",
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorisieren",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}