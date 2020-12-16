package com.example.protodatastoresample.protoserializer

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.protodatastoresample.JobProto
import com.example.protodatastoresample.SettingProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * Serializer to read/write data of type SettingProto
 */
object SettingsProtoSerializer : Serializer<SettingProto> {
    override fun readFrom(input: InputStream): SettingProto {
        try {
            return SettingProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: SettingProto, output: OutputStream) {
        t.writeTo(output)
    }
}