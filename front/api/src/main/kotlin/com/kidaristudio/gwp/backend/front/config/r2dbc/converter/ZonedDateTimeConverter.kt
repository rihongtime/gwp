package com.kidaristudio.gwp.backend.front.config.r2dbc.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@ReadingConverter
class ZonedDateTimeReadConverter : Converter<LocalDateTime?, ZonedDateTime?> {
    override fun convert(date: LocalDateTime): ZonedDateTime {
        return date.atZone(ZoneOffset.UTC)
    }
}

@WritingConverter
class ZonedDateTimeWriteConverter : Converter<ZonedDateTime?, LocalDateTime?> {
    override fun convert(zonedDateTime: ZonedDateTime): LocalDateTime {
        return LocalDateTime.from(zonedDateTime.toLocalDateTime())
    }
}