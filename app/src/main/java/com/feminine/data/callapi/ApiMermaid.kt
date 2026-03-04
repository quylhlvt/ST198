package com.feminine.data.callapi

import com.feminine.data.model.CharacterResponse
import retrofit2.http.GET

interface ApiMermaid {
    @GET("api/ST198_FeminineMaker")
    suspend fun getAllData(): CharacterResponse
}