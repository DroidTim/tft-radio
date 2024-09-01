package de.iu.tftradio.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Unit //Data model
    ): Response<ResponseBody>

    @GET
    suspend fun get(
        @Url url: String
    ): Response<ResponseBody> //Response<DataModel>
}