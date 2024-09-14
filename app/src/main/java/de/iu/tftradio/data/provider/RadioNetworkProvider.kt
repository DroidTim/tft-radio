package de.iu.tftradio.data.provider


import de.iu.tftradio.data.api.ApiService
import de.iu.tftradio.data.error.NetworkException
import de.iu.tftradio.data.model.PlaylistDto
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
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

    suspend fun get(): Response<PlaylistDto> {
        return retrofitInstance.get("/playlist")
    }
}