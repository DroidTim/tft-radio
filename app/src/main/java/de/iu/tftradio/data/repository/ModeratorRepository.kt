package de.iu.tftradio.data.repository

import android.accounts.NetworkErrorException
import de.iu.tftradio.data.model.ModeratorFeedback
import de.iu.tftradio.data.model.ModeratorFeedbackStars
import de.iu.tftradio.data.provider.ExampleDataModeratorFeedback
import de.iu.tftradio.data.provider.RadioMemoryProvider
import de.iu.tftradio.data.provider.RadioNetworkProvider

internal class ModeratorRepository {

    private val radioNetworkProvider = RadioNetworkProvider()
    private val radioMemoryProvider = RadioMemoryProvider<List<ModeratorFeedback>>()

    @Throws(NetworkErrorException::class)
    suspend fun getModeratorFeedbackList(clearCache: Boolean = false): List<ModeratorFeedback> {
        if (clearCache) radioMemoryProvider.clean()
        return radioMemoryProvider.retrieve() ?: radioMemoryProvider.cacheAndRetrieve(
            data = radioNetworkProvider.getModeratorFeedbackList().body() ?: throw NetworkErrorException()
        )
    }

    fun getModeratorFavoriteList(): List<ModeratorFeedback> {
        return ExampleDataModeratorFeedback().moderatorFeedbackList
    }

    @Throws(NetworkErrorException::class)
    suspend fun postModeratorFeedback(moderatorFeedbackStars: ModeratorFeedbackStars) {
        takeIf { radioNetworkProvider.postModeratorFeedback(moderatorFeedbackStars = moderatorFeedbackStars).isSuccessful } ?: throw NetworkErrorException()
    }

}