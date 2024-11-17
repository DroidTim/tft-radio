package de.iu.tftradio.data.provider


import de.iu.tftradio.data.api.ApiService
import de.iu.tftradio.data.error.NetworkException
import de.iu.tftradio.data.model.ModeratorFeedback
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.model.PlaylistDto
import de.iu.tftradio.data.model.SongRequestList
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

internal class RadioNetworkProvider {

    private val retrofitInstance = setApiService()

    /**
     * Configuration of the network settings.
     */
    @Throws(NetworkException::class)
    private fun setApiService(): ApiService {
        val baseUrl = "https://tft-radio.resources"
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(ApiService::class.java)
        } catch (error: Exception) {
            throw NetworkException()
        }
    }

    suspend fun getPlaylist(): Response<PlaylistDto> {
        return retrofitInstance.getPlaylist(url = "/playlist")
    }

    suspend fun getModeratorFeedbackList(): Response<List<ModeratorFeedback>> {
        return retrofitInstance.getModeratorFeedbackList(url = "/moderatorFeedbackList")
    }

    suspend fun getRequestSongList(): Response<SongRequestList> {
        return retrofitInstance.getSongRequestList(url = "/requestSongList")
    }

    suspend fun postSongVote(songIdentifier: String): Response<Unit> {
        return retrofitInstance.postSongVote(url = "/requestSongVote", body = songIdentifier)
    }

    suspend fun postSongVoteOut(songIdentifier: String): Response<Unit> {
        return retrofitInstance.postSongVoteOut(url = "/requestSongVoteOut", body = songIdentifier)
    }

    suspend fun postSongRequest(songTitle: String): Response<Unit> {
        return retrofitInstance.postSongRequest(url = "/songTitle", body = songTitle)
    }

    suspend fun postSongFavorite(songIdentifier: String): Response<Unit> {
        return retrofitInstance.postSongFavorite(url = "/songFavorite", body = songIdentifier)
    }

    suspend fun postSongFavoriteOut(songIdentifier: String): Response<Unit> {
        return retrofitInstance.postSongFavoriteOut(url = "/songFavoriteOut", body = songIdentifier)
    }

    suspend fun postModeratorFeedback(moderatorFeedbackStars: ModeratorFeedbackStars): Response<Unit> {
        return retrofitInstance.postModeratorStars(url = "/moderatorFeedback", body = moderatorFeedbackStars)
    }
}