package de.iu.tftradio.data.api

import de.iu.tftradio.data.model.ModeratorFeedback
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.model.SongRequestList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

internal interface ApiService {
    @POST
    suspend fun postSongFavorite(
        @Url url: String,
        @Body body: String
    ): Response<Unit>

    @POST
    suspend fun postSongFavoriteOut(
        @Url url: String,
        @Body body: String
    ): Response<Unit>

    @POST
    suspend fun postSongVote(
        @Url url: String,
        @Body body: String
    ): Response<Unit>

    @POST
    suspend fun postSongVoteOut(
        @Url url: String,
        @Body body: String
    ): Response<Unit>

    @POST
    suspend fun postSongRequest(
        @Url url: String,
        @Body body: String
    ): Response<Unit>

    @POST
    suspend fun postModeratorStars(
        @Url url: String,
        @Body body: ModeratorFeedbackStars
    ): Response<Unit>

    @GET
    suspend fun getPlaylist(
        @Url url: String
    ): Response<PlaylistDto>

    @GET
    suspend fun getSongRequestList(
        @Url url: String
    ): Response<SongRequestList>

    @GET
    suspend fun getModeratorFeedbackList(
        @Url url: String
    ): Response<List<ModeratorFeedback>>
}