package com.jmat.encode.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.jmat.powertools.EncodeStore
import java.io.InputStream
import java.io.OutputStream

object EncodeStoreSerializer : Serializer<EncodeStore> {
    override val defaultValue: EncodeStore = EncodeStore.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): EncodeStore {
        try {
            return EncodeStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: EncodeStore, output: OutputStream) = t.writeTo(output)
}
