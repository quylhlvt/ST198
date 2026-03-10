package com.female.maker.oc.creator.data.callapi

import com.female.maker.oc.creator.data.model.CharacterResponse
import retrofit2.http.GET

interface ApiMermaid {
    @GET("api/ST198_FeminineMaker")
    suspend fun getAllData(): CharacterResponse
}