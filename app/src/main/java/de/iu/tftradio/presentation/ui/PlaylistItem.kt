package de.iu.tftradio.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
internal fun PlaylistItem(
    modifier: Modifier,
    url: String,
    interpret: String,
    title: String,
    album: String,
    favoriteCount: Int,
    isOnTrack: Boolean,
    isFavorite: Boolean,
    onFavorite: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(200.dp),
        onClick = { /*TODO*/ },
        border = if (isOnTrack) BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SongCover(
                url = url
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Album(text = album, modifier = Modifier.padding(vertical = 8.dp))
                Title(text = title, modifier = Modifier.padding(vertical = 16.dp))
                Interpret(text = interpret, modifier = Modifier.padding(vertical = 8.dp))
            }
            Box(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (isOnTrack) {
                    OnTrack()
                } else Box(modifier = Modifier)
                Favorite(
                    favoriteCount = favoriteCount,
                    isFavorite = isFavorite,
                    onFavorite = onFavorite
                )
            }
        }
    }
}

@Composable
fun OnTrack(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(30.dp)
        .clip(CircleShape)
        .background(color = MaterialTheme.colorScheme.primary))
}
@Composable
fun Favorite(
    favoriteCount: Int,
    isFavorite: Boolean,
    onFavorite: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val modifier = Modifier.size(40.dp).clickable { onFavorite.invoke() }
        if (isFavorite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = modifier,
                tint = MaterialTheme.colorScheme.error
            )
        } else {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = modifier
            )
        }

        Text(text = favoriteCount.toString(), style = MaterialTheme.typography.bodySmall)
    }
    
}

@Composable
fun Album(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}

@Composable
fun Title(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.headlineMedium, modifier = modifier)
}

@Composable
fun Interpret(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}

@Composable
fun SongCover(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(30.dp))
    )
}

@Preview
@Composable
fun SongPicturePreview(modifier: Modifier = Modifier) {
    SongCover(
        url = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg"
    )
}

@Preview
@Composable
fun PlayListItemPreview() {
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