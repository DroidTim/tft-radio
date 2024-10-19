package de.iu.tftradio.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class PlaylistDto(
    @JsonProperty("moderator") val moderator: Moderator,
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

@JsonIgnoreProperties
data class Moderator(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("stars") val stars: Int,
    @JsonProperty("trend") val trend: Trend
)

@JsonIgnoreProperties
data class ModeratorFeedbackStars(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("stars") val stars: Int
)

@JsonIgnoreProperties
enum class Trend {
    @JsonProperty POSITIV,
    @JsonProperty NEGATIV,
    @JsonProperty EQUAL
}