package com.example.protodatastoresample.protoserializer

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.protodatastoresample.JobProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * Serializer to read/write data of type JobProto
 */
object JobProtoSerializer : Serializer<JobProto> {
    override fun readFrom(input: InputStream): JobProto {
        try {
            return JobProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: JobProto, output: OutputStream) {
        t.writeTo(output)
    }
}