package de.iu.tftradio.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.iu.tftradio.presentation.ErrorScreen
import de.iu.tftradio.presentation.viewModel.PlaylistViewModel
import de.iu.tftradio.presentation.viewModel.UiState

@Composable
internal fun PlayList(viewModel: PlaylistViewModel, modifier: Modifier) {
    val rememberLazyListState = rememberLazyListState()
    var onTrackItem by rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(key1 = viewModel) {
        viewModel.loadPlaylist()
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if(viewModel.errorDialog) {
            //Fehlerdialog
        }
        when (val state = viewModel.uiState.collectAsState().value) {
            UiState.Loading -> CircularProgressIndicator(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize())
            is UiState.Success -> {
                val playlist = state.playlistDto
                LaunchedEffect(key1 = playlist) {
                    playlist.playlist.forEachIndexed { index, playlistItemDto ->
                        if (playlistItemDto.onTrack) {
                            onTrackItem = index
                        }
                    }
                    rememberLazyListState.scrollToItem(onTrackItem)
                }
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    state = rememberLazyListState
                ) {
                    item { 
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(text = playlist.moderator,
                                modifier = Modifier.padding(end = 8.dp ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Icon(
                                imageVector = Icons.Default.Comment,
                                contentDescription = null)
                        }
                        
                    }
                    items(playlist.playlist) { playlistItem ->
                            PlaylistItem(
                                modifier = Modifier,
                                url = playlistItem.pictureSource,
                                interpret = playlistItem.interpret,
                                title = playlistItem.title,
                                album = playlistItem.album,
                                favoriteCount = playlistItem.favoriteCount,
                                isOnTrack = playlistItem.onTrack,
                                isFavorite = false,
                                onFavorite = {
                                    viewModel.setSongFavorite(songIdentifier = playlistItem.identifier)
                                }
                            )
                    }
                }
            }

            is UiState.Failure -> {
                ErrorScreen(
                    exception = state.exception,
                    onRetry = {
                        viewModel.loadPlaylist()
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun PlayListPreview(modifier: Modifier = Modifier) {
    val rememberLazyListState = rememberLazyListState()
    LaunchedEffect(key1 = null) {
        rememberLazyListState.animateScrollToItem(4)
    }
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        state = rememberLazyListState
    ) {
        item {
            Text(text = "Playlist", style = MaterialTheme.typography.titleLarge)
        }
        item {
            PlaylistItem(
                modifier = Modifier,
                url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                interpret = "Interpret",
                title = "Titel",
                album = "Album",
                favoriteCount = 12,
                isOnTrack = true,
                onFavorite = {},
                isFavorite = true
            )
        }
        item {
            PlaylistItem(
                modifier = Modifier,
                url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                interpret = "Interpret",
                title = "Titel",
                album = "Album",
                favoriteCount = 12,
                isOnTrack = false,
                onFavorite = {},
                isFavorite = true
            )
        }
        item {
            PlaylistItem(
                modifier = Modifier,
                url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                interpret = "Interpret",
                title = "Titel",
                album = "Album",
                favoriteCount = 12,
                isOnTrack = false,
                onFavorite = {},
                isFavorite = true
            )
        }
        item {
            PlaylistItem(
                modifier = Modifier,
                url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                interpret = "Interpret",
                title = "Titel",
                album = "Album",
                favoriteCount = 12,
                isOnTrack = false,
                onFavorite = {},
                isFavorite = true
            )
        }
        item {
            PlaylistItem(
                modifier = Modifier,
                url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                interpret = "Interpret",
                title = "Titel",
                album = "Album",
                favoriteCount = 12,
                isOnTrack = false,
                onFavorite = {},
                isFavorite = true
            )
        }
    }

}