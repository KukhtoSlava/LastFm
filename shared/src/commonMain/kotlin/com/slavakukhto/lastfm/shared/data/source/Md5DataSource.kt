package com.slavakukhto.lastfm.shared.data.source

import com.slavakukhto.lastfm.shared.data.responseentity.MD5Response
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

interface HashApi {

    suspend fun getHash(apiSign: String): MD5Response
}

class Md5HashApi(private val httpClient: HttpClient) : HashApi {

    override suspend fun getHash(apiSign: String): MD5Response {
        return httpClient.get(BASE_URL) {
            body = FormDataContent(Parameters.build {
                append(KEY_VALUE, apiSign)
            })
        }
    }

    private companion object {

        private const val BASE_URL = "https://api.hashify.net/hash/md5/hex"
        private const val KEY_VALUE = "value"
    }
}
