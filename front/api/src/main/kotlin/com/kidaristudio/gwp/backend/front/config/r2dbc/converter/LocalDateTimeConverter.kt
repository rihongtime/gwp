package com.kidaristudio.gwp.backend.front.config.r2dbc.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.joda.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

@ReadingConverter
class LocalDateTimeReadConverter : Converter<LocalDateTime?, java.time.LocalDateTime?> {
    override fun convert(date: LocalDateTime): java.time.LocalDateTime {
        return java.time.LocalDateTime.ofInstant(date.toDate().toInstant(), ZoneId.systemDefault())
    }
}

@WritingConverter
class LocalDateTimeWriteConverter : Converter<java.time.LocalDateTime?, LocalDateTime?> {
    override fun convert(localDateTime: java.time.LocalDateTime): LocalDateTime {
        return LocalDateTime.fromDateFields(Date(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()))
    }
}