package de.iu.tftradio.data.api

import de.iu.tftradio.data.model.PlaylistDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST
    suspend fun postSongFavorite(
        @Url url: String,
        @Body body: String
    ): Response<ResponseBody>

    @GET
    suspend fun getPlaylist(
        @Url url: String
    ): Response<PlaylistDto>
}