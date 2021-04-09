package com.kidaristudio.gwp.backend.front.core.model.response.contents

import com.kidaristudio.gwp.backend.persistence.contents.entity.Contents
import java.time.LocalDateTime

data class ContentsResponse(
    var id: Long = 0,
    var state: String,
    var schedule: String? = null,
    var comment: String? = null,
    var publishedAt: LocalDateTime? = null,
    var newPublishedAt: LocalDateTime? = null,
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