package com.faraji.challenge.tmdb.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonFactory private constructor() {
    val singletonGson: Gson by lazy {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    companion object {
        val instance by lazy { GsonFactory() }
    }
}