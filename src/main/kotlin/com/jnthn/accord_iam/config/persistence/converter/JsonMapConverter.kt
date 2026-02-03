package com.jnthn.accord_iam.config.persistence.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false)
class JsonMapConverter(
    private val objectMapper: ObjectMapper = ObjectMapper()
) : AttributeConverter<Map<String, Any>, String> {

    override fun convertToDatabaseColumn(attribute: Map<String, Any>?): String {
        return objectMapper.writeValueAsString(attribute ?: emptyMap<String, Any>())
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any> {
        if (dbData.isNullOrBlank()) return emptyMap()
        return objectMapper.readValue(
            dbData,
            objectMapper.typeFactory.constructMapType(
                Map::class.java,
                String::class.java,
                Any::class.java
            )
        )
    }
}