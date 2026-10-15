package sk.o2.scratchcard.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface O2Api {

    @GET("version")
    suspend fun getVersion(
        @Query("code") code: String
    ): VersionDto
}
