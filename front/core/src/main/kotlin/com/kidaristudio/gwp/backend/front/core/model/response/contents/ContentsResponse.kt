package com.kidaristudio.gwp.backend.front.core.model.response.contents

import com.kidaristudio.gwp.backend.persistence.contents.entity.Contents
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "콘텐츠 정보")
data class ContentsResponse(
    @Schema(description = "아이디", nullable = false)
    var id: Long = 0,
    @Schema(description = "상태", nullable = false)
    var state: String,
    @Schema(description = "기간", nullable = true , example = "yyMMdd")
    var schedule: String? = null,
    @Schema(description = "아이디", nullable = true)
    var comment: String? = null,
    @Schema(description = "발행일", nullable = true , example = "yyMMdd")
    var publishedAt: LocalDateTime? = null,
    @Schema(description = "신규발행일", nullable = true , example = "yyMMdd")
    var newPublishedAt: LocalDateTime? = null,
    @Schema(description = "만료일", nullable = true , example = "yyMMdd")
    var expiredAt: LocalDateTime? = null,
) {
}

fun Contents.toResponse() =
    ContentsResponse(
        id = id,
        state = state.name,
        comment = comment,
        publishedAt = publishedAt,
        newPublishedAt = newPublishedAt,
        expiredAt = newPublishedAt
    )