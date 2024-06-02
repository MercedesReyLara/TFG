package com.example.tfg.petitionsAndFunctions
import com.google.gson.*
import android.util.Base64
import java.lang.reflect.Type
class ByteArrayFunctions : JsonSerializer<ByteArray>, JsonDeserializer<ByteArray> {
        override fun serialize(src: ByteArray?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP))
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ByteArray {
            return Base64.decode(json?.asString, Base64.NO_WRAP)
        }
    }