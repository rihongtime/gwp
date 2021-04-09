package com.kidaristudio.gwp.backend.persistence.contents.enum

enum class ContentsState {
    SCHEDULED,
    DELAYED,
    POSTPONED,
    SUSPENDED,
    SEASON_END,
    COMPLETED,
    DELETED;

    companion object {
        fun of(kind: String) = runCatching {
            valueOf(kind.toLowerCase().trim())
        }.getOrElse { SCHEDULED }
    }
}