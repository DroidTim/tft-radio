package de.iu.tftradio.data.provider

import de.iu.tftradio.data.model.Moderator
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.model.SongItemDto
import de.iu.tftradio.data.model.ModeratorFeedback
import de.iu.tftradio.data.model.SongRequestList
import de.iu.tftradio.data.model.Trend

internal data class ExampleDataPlaylist(
    val playlist: PlaylistDto = PlaylistDto(
        moderator = Moderator(
            identifier = "8974343d293",
            name = "John Doe",
            averageRating = 3,
            trend = Trend.POSITIV
        ),
        playlist = listOf(
            SongItemDto(
                identifier = "2314343dfsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 1",
                album = "Album Name 1",
                interpret = "Artist 1",
                onTrack = false,
                votesCount = 23
            ),
            SongItemDto(
                identifier = "2314243ffsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 2",
                album = "Album Name 2",
                interpret = "Artist 2",
                onTrack = false,
                votesCount = 26
            ),
            SongItemDto(
                identifier = "d314343dfsb",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 3",
                album = "Album Name 3",
                interpret = "Artist 3",
                onTrack = false,
                votesCount = 2
            ),
            SongItemDto(
                identifier = "23a7343dfea",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 4",
                album = "Album Name 4",
                interpret = "Artist 4",
                onTrack = true,
                votesCount = 76
            ),
            SongItemDto(
                identifier = "2314321adsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 5",
                album = "Album Name 5",
                interpret = "Artist 5",
                onTrack = false,
                votesCount = 3
            ),
            SongItemDto(
                identifier = "2df4343dkua",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 6",
                album = "Album Name 6",
                interpret = "Artist 6",
                onTrack = false,
                votesCount = 90
            )
        )
    )
)

internal data class ExampleRequestSongList(
    val songRequest: SongRequestList = SongRequestList(
        songRequest = listOf(
            SongItemDto(
                identifier = "2314343dfsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 1",
                album = "Album Name 1",
                interpret = "Artist 1",
                onTrack = false,
                votesCount = 423
            ),
            SongItemDto(
                identifier = "2314243ffsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 2",
                album = "Album Name 2",
                interpret = "Artist 2",
                onTrack = false,
                votesCount = 326
            ),
            SongItemDto(
                identifier = "d314343dfsb",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 3",
                album = "Album Name 3",
                interpret = "Artist 3",
                onTrack = false,
                votesCount = 324
            ),
            SongItemDto(
                identifier = "23a7343dfea",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 4",
                album = "Album Name 4",
                interpret = "Artist 4",
                onTrack = true,
                votesCount = 176
            ),
            SongItemDto(
                identifier = "2314321adsa",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 5",
                album = "Album Name 5",
                interpret = "Artist 5",
                onTrack = false,
                votesCount = 99
            ),
            SongItemDto(
                identifier = "2df4343dkua",
                pictureSource = "https://mh-2-bildagentur.panthermedia.net/images/cms/Landingpage-Category-Nature/Icon-Fruehling.jpg",
                title = "Song Title 6",
                album = "Album Name 6",
                interpret = "Artist 6",
                onTrack = false,
                votesCount = 2
            )
        )
    )
)

internal data class ExampleDataModeratorFeedback(
    val moderatorFeedbackList: List<ModeratorFeedback> = listOf(
        ModeratorFeedback(
            rating = 4,
            comment = "Super Moderator"
        ),
        ModeratorFeedback(
            rating = 2,
            comment = "Super Moderator"
        ),
        ModeratorFeedback(
            rating = 1,
            comment = "Super Moderator"
        )
    )
)