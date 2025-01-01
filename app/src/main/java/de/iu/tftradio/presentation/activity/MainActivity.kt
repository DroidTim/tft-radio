package de.iu.tftradio.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LibraryAddCheck
import androidx.compose.material.icons.filled.QueuePlayNext
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.rememberLibraries
import de.iu.tftradio.R
import de.iu.tftradio.presentation.model.Tab
import de.iu.tftradio.presentation.theme.TftradioTheme
import de.iu.tftradio.presentation.ui.PlayList
import de.iu.tftradio.presentation.ui.SongRequest

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var tab by rememberSaveable { mutableStateOf(Tab.SONG_LIST) }
            TftradioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = stringResource(id = R.string.app_name))
                                }
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {
                                        tab = Tab.SONG_LIST
                                    },
                                    icon = {
                                        Image(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Playlist", colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary))
                                    }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {
                                        tab = Tab.SONG_REQUEST
                                    },
                                    icon = {
                                        Image(imageVector = Icons.Default.QueuePlayNext, contentDescription = "Feedback", colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary))
                                    }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {
                                        tab = Tab.License
                                    },
                                    icon = {
                                        Image(imageVector = Icons.Default.LibraryAddCheck, contentDescription = "Lizenzen", colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary))
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        when(tab) {
                            Tab.SONG_LIST -> PlayList(playlistViewModel = viewModel(), moderatorFeedbackViewModel = viewModel(), modifier = Modifier.padding(innerPadding))
                            Tab.SONG_REQUEST -> SongRequest(songRequestViewModel = viewModel(), modifier = Modifier.padding(innerPadding))
                            Tab.License -> License(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }

}


@Composable
private fun License(modifier: Modifier) {
    LibrariesContainer(
        modifier = Modifier.fillMaxSize()
    )
}