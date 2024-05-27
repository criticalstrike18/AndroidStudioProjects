package com.example.filterandrefine.utils

import com.example.filterandrefine.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


object Supabaseclient {
    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.supabaseUrl,
        supabaseKey = BuildConfig.supabaseKey,
    ) {
        install(Postgrest)
    }
}