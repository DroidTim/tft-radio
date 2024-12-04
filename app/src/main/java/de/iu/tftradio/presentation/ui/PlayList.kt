package de.iu.tftradio.presentation.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.tftradio.R
import de.iu.tftradio.data.model.Moderator
import de.iu.tftradio.data.model.Trend
import de.iu.tftradio.presentation.ErrorScreen
import de.iu.tftradio.presentation.viewModel.ModeratorFeedbackViewModel
import de.iu.tftradio.presentation.viewModel.PlaylistViewModel
import de.iu.tftradio.presentation.viewModel.helper.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayList(playlistViewModel: PlaylistViewModel, moderatorFeedbackViewModel: ModeratorFeedbackViewModel, modifier: Modifier) {
    val rememberLazyListState = rememberLazyListState()
    var onTrackItem by rememberSaveable { mutableIntStateOf(0) }
    var showModeratorFeedbackDialog by rememberSaveable { mutableStateOf(false) }
    val refreshScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var feedbackText by rememberSaveable { mutableStateOf("") }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        refreshScope.launch {
            //For displaying the refresh logic in case of use example data
            delay(2000)
            playlistViewModel.loadPlaylist(getExampleData = true)
            isRefreshing = false
        }
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = playlistViewModel) {
        playlistViewModel.initialize(sharedPreferences = context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE))
        playlistViewModel.loadPlaylist(getExampleData = true)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        if (moderatorFeedbackViewModel.errorDialog) {
            AlertDialog(
                title = {
                    Text(text = stringResource(id = R.string.title_error_dialog))
                },
                text = {
                    Text(text = stringResource(id = R.string.label_error_dialog))
                },
                onDismissRequest = {
                    moderatorFeedbackViewModel.errorDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = { moderatorFeedbackViewModel.errorDialog = false }
                    ) {
                        Text(text = stringResource(id = R.string.ok_button))
                    }
                }
            )
        }

        if(showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                RatingsSection(viewModel = viewModel())
            }
        }

        when (val state = playlistViewModel.uiState.collectAsState().value) {
            UiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )

            is UiState.Success -> {
                val playlist = state.data
                LaunchedEffect(key1 = playlist) {
                    playlist.playlist.forEachIndexed { index, playlistItemDto ->
                        if (playlistItemDto.onTrack) {
                            onTrackItem = index
                        }
                    }
                    rememberLazyListState.scrollToItem(onTrackItem)
                }
                if (showModeratorFeedbackDialog) {
                    var stars by rememberSaveable { mutableIntStateOf(0) }
                    var isLoading by rememberSaveable { mutableStateOf(false) }
                    AlertDialog(
                        title = {
                            Text(text = stringResource(id = R.string.title_dialog_moderator_feedback))
                        },
                        text = {
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth()
                                    .padding(top = 8.dp))
                            } else {
                                Column {
                                    FeedbackModerator(
                                        onClick = {
                                            stars = it
                                        }
                                    )
                                    TextField(
                                        modifier = Modifier.padding(top = 8.dp),
                                        value = feedbackText,
                                        onValueChange = {
                                            feedbackText = it
                                        }
                                    )
                                }
                            }
                        },
                        onDismissRequest = {
                            if (!isLoading) showModeratorFeedbackDialog = false
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showModeratorFeedbackDialog = false },
                                enabled = !isLoading
                            ) {
                                Text(text = stringResource(id = R.string.cancel_button))
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    isLoading = true
                                    moderatorFeedbackViewModel.setModeratorFeedback(
                                        moderatorIdentifier = playlist.moderator.identifier,
                                        stars = stars,
                                        comment = feedbackText,
                                        onComplete = {
                                            it.onSuccess {
                                                playlistViewModel.loadPlaylist(getExampleData = true, clearCache = true)
                                            }.onFailure {
                                                moderatorFeedbackViewModel.errorDialog = true
                                            }
                                            isLoading = false
                                            showModeratorFeedbackDialog = false
                                        }
                                    )
                                },
                                enabled = !isLoading
                            ) {
                                Text(text = stringResource(id = R.string.confirm_button))
                            }
                        }
                    )
                }
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    state = refreshState,
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Moderator(
                            moderator = playlist.moderator,
                            onModeratorStar = {
                                showModeratorFeedbackDialog = true
                            },
                            onModerator = {
                                showBottomSheet = true
                            }
                        )
                        LazyColumn(
                            state = rememberLazyListState
                        ) {
                            items(playlist.playlist) { playlistItem ->
                                var isFavorite by rememberSaveable { mutableStateOf(playlistViewModel.getSongVoted(playlistItem.identifier)) }
                                var favoriteCount by rememberSaveable { mutableIntStateOf(playlistItem.votesCount) }
                                PlaylistItem(
                                    modifier = Modifier,
                                    url = playlistItem.pictureSource,
                                    interpret = playlistItem.interpret,
                                    title = playlistItem.title,
                                    album = playlistItem.album,
                                    favoriteCount = favoriteCount,
                                    isOnTrack = playlistItem.onTrack,
                                    onVote = isFavorite,
                                    onVoteAction = {
                                        //same user behavior as Instagram
                                        if (isFavorite) {
                                            favoriteCount -= 1
                                        } else {
                                            favoriteCount += 1
                                        }
                                        playlistViewModel.setSongFavorite(
                                            songIdentifier = playlistItem.identifier,
                                            isFavorite = isFavorite
                                        )
                                        isFavorite = !isFavorite
                                    }
                                )
                            }
                        }
                    }
                }
            }

            is UiState.Failure -> {
                ErrorScreen(
                    exception = state.exception,
                    onRetry = {
                        playlistViewModel.loadPlaylist(getExampleData = true)
                    }
                )
            }
        }
    }
}

@Composable
private fun Moderator(
    moderator: Moderator,
    onModeratorStar: () -> Unit,
    onModerator: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = moderator.name,
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable(onClick = onModerator),
            style = MaterialTheme.typography.bodyMedium
        )
        ModeratorStars(
            stars = moderator.averageRating,
            onClick = onModeratorStar
        )
        Image(
            imageVector = when (moderator.trend) {
                Trend.NEGATIV -> Icons.Default.ArrowDownward
                Trend.POSITIV -> Icons.Default.ArrowUpward
                Trend.EQUAL -> Icons.AutoMirrored.Filled.ArrowForward
            },
            colorFilter = when (moderator.trend) {
                Trend.NEGATIV -> ColorFilter.tint(color = Color.Red)
                Trend.POSITIV -> ColorFilter.tint(color = Color.Green)
                Trend.EQUAL -> ColorFilter.tint(color = Color.Magenta)
            },
            contentDescription = "trend"
        )
    }
}

@Composable
internal fun ModeratorStars(
    stars: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        for (i in 1..5) {
            Image(imageVector = if (i <= stars) Icons.Default.Star else Icons.Default.StarOutline, contentDescription = "Star $i")
        }
    }
}

@Composable
private fun FeedbackModerator(
    onClick: (rating: Int) -> Unit
) {
    var rating by rememberSaveable { mutableIntStateOf(0) }
    Row {
        for (i in 1..5) {
            IconButton(
                onClick = {
                    rating = if (rating == i) 0 else i
                    onClick(rating)
                }
            ) {
                Icon(
                    imageVector = if (i <= rating) {
                        Icons.Default.Star
                    } else Icons.Default.StarOutline,
                    contentDescription = "Star $i",
                    modifier = Modifier.size(40.dp)
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
                onVoteAction = {},
                onVote = true
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
                onVoteAction = {},
                onVote = true
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
                onVoteAction = {},
                onVote = true
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
                onVoteAction = {},
                onVote = true
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
                onVoteAction = {},
                onVote = true
            )
        }
    }

}