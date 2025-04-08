package com.example.financialtrackerapp.data.entity.temporary

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object GlobalSerializer : Serializer<GlobalData> {
    override val defaultValue: GlobalData
        get() = GlobalData()

    override suspend fun readFrom(input: InputStream): GlobalData {
        return try {
            Json.decodeFromString(
                deserializer = GlobalData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            GlobalData(username = "reading_failure")
        }
    }

    override suspend fun writeTo(t: GlobalData, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = GlobalData.serializer(),
                    value = t
                )
                    .encodeToByteArray()
            )
        }
    }
}