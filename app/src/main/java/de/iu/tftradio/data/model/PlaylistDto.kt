package de.iu.tftradio.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class PlaylistDto(
    @JsonProperty("moderator") val moderator: String,
    @JsonProperty("playlist") val playlist: List<PlaylistItemDto>
)


@JsonIgnoreProperties
data class PlaylistItemDto(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("picture") val pictureSource: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("album") val album: String,
    @JsonProperty("interpret") val interpret: String,
    @JsonProperty("onTrack") val onTrack: Boolean,
    @JsonProperty("favoriteCount") val favoriteCount: Int
)