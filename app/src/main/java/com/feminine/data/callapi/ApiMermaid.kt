package com.feminine.data.callapi

import com.feminine.data.model.CharacterResponse
import retrofit2.http.GET

interface ApiMermaid {
    @GET("api/ST214_AvatarProfileMaker")
    suspend fun getAllData(): CharacterResponse
}