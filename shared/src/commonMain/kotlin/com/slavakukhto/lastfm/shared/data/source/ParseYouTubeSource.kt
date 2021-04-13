package com.slavakukhto.lastfm.shared.data.source

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

interface ParseYouTubeSource {

    suspend fun getBody(url: String): String
}

class ParseYouTubeSourceImpl(private val httpClient: HttpClient) : ParseYouTubeSource {

    override suspend fun getBody(url: String): String {
        val response = httpClient.get<HttpResponse>(url)
        val body = response.readText()
        return body
    }
}
