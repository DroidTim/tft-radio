package de.iu.tftradio.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
internal data class PlaylistDto(
    @JsonProperty("moderator") val moderator: Moderator,
    @JsonProperty("playlist") val playlist: List<SongItemDto>
)

@JsonIgnoreProperties
internal data class SongRequestList(
    @JsonProperty("songRequest") val songRequest: List<SongItemDto>
)

@JsonIgnoreProperties
internal data class SongItemDto(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("picture") val pictureSource: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("album") val album: String,
    @JsonProperty("interpret") val interpret: String,
    @JsonProperty("onTrack") val onTrack: Boolean,
    @JsonProperty("votesCount") val votesCount: Int
)

@JsonIgnoreProperties
internal data class Moderator(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("averageRating") val averageRating: Int,
    @JsonProperty("trend") val trend: Trend
)

@JsonIgnoreProperties
internal data class ModeratorFeedbackStars(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("stars") val stars: Int,
    @JsonProperty("comment") val comment: String
)

@JsonIgnoreProperties
internal enum class Trend {
    @JsonProperty POSITIV,
    @JsonProperty NEGATIV,
    @JsonProperty EQUAL
}