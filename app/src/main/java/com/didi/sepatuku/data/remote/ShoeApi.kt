package com.didi.sepatuku.data.remote

import com.didi.sepatuku.data.remote.dto.ShoeDto
import retrofit2.http.GET

interface ShoeApi {
    @GET("prasetyodidi/shoes-app-data/main/shoes-detail.json")
    suspend fun getAllShoes(): List<ShoeDto>

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}