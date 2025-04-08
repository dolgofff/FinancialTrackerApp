package com.example.financialtrackerapp.data.entity.temporary

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object TokenSerializer : Serializer<TokenData> {
    override val defaultValue: TokenData
        get() = TokenData()

    override suspend fun readFrom(input: InputStream): TokenData {
        return try {
            Json.decodeFromString(
                deserializer = TokenData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            TokenData(token = "reading_failure")
        }
    }

    override suspend fun writeTo(t: TokenData, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = TokenData.serializer(),
                    value = t
                )
                    .encodeToByteArray()
            )
        }
    }
}