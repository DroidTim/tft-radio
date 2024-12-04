package de.iu.tftradio.presentation.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import de.iu.tftradio.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.iu.tftradio.data.model.SongRequestList
import de.iu.tftradio.presentation.viewModel.SongRequestViewModel
import de.iu.tftradio.presentation.viewModel.helper.UiState

@Composable
internal fun SongRequest(modifier: Modifier, songRequestViewModel: SongRequestViewModel) {

    val context = LocalContext.current

    LaunchedEffect(key1 = songRequestViewModel) {
        songRequestViewModel.initialize(sharedPreferences = context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE))
        songRequestViewModel.loadPlaylist(getExampleData = true)
    }

    when (val state = songRequestViewModel.uiState.collectAsState().value) {
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            SongRequest(modifier = modifier, requestSongRequestList = state.data, songRequestViewModel = songRequestViewModel)
        }

        is UiState.Failure -> {

        }
    }

}

@Composable
private fun SongRequest(
    modifier: Modifier,
    requestSongRequestList: SongRequestList,
    songRequestViewModel: SongRequestViewModel
) {
    var currentTitle by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.subtitle_song_request_title), style = MaterialTheme.typography.titleMedium)
        TextField(
            value = TextFieldValue(currentTitle),
            onValueChange = { currentTitle = it.text },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray),
            placeholder = { Text(text = stringResource(id = R.string.input_field_song_request_title)) },
            trailingIcon = {
                IconButton(
                    onClick = {
                        songRequestViewModel.postSongRequest(songTitle = currentTitle)
                        currentTitle = ""
                        keyboardController?.hide()
                        songRequestViewModel.loadPlaylist(getExampleData = true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(id = R.string.input_field_song_request_send_button_description),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text("Songwünsche:", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        SongRequestList(
            requestSongRequestList = requestSongRequestList,
            songRequestViewModel = songRequestViewModel
        )
    }
}

@Composable
private fun SongRequestList(
    requestSongRequestList: SongRequestList,
    songRequestViewModel: SongRequestViewModel
) {
    LazyColumn {
        items(requestSongRequestList.songRequest) { songItem ->
            var isVote by rememberSaveable { mutableStateOf(songRequestViewModel.getSongVoted(songIdentifier = songItem.identifier)) }
            var votesCount by rememberSaveable { mutableIntStateOf(songItem.votesCount) }
            PlaylistItem(
                modifier = Modifier,
                url = songItem.pictureSource,
                interpret = songItem.interpret,
                title = songItem.title,
                album = songItem.album,
                favoriteCount = votesCount,
                isOnTrack = false,
                onVote = isVote,
                onVoteAction = {
                    //same user behavior as Instagram
                    if (isVote) {
                        votesCount -= 1
                    } else {
                        votesCount += 1
                    }
                    songRequestViewModel.postSongVote(
                        songIdentifier = songItem.identifier,
                        isVote = isVote
                    )
                    isVote = !isVote
                }
            )
        }
    }
}

