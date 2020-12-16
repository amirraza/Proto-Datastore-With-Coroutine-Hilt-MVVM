package com.example.protodatastoresample.protoserializer

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.protodatastoresample.UserProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * Serializer to read/write data of type USerProto
 */
object UserProtoSerializer : Serializer<UserProto> {
    override fun readFrom(input: InputStream): UserProto {
        try {
            return UserProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: UserProto, output: OutputStream) {
        t.writeTo(output)
    }
}
