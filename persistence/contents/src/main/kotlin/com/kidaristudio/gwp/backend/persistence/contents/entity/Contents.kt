package com.kidaristudio.gwp.backend.persistence.contents.entity

import com.kidaristudio.gwp.backend.persistence.contents.enum.ContentsState
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.time.LocalDateTime

@Table("contents")
class Contents : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    @Id
    var id: Long = 0
    var state: ContentsState = ContentsState.SCHEDULED
    var schedule: String? = null
    var comment: String? = null
    var publishedAt: LocalDateTime? = null
    var newPublishedAt: LocalDateTime? = null
    var expiredAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null

}