package com.kidaristudio.gwp.backend.persistence.contents.service

import com.kidaristudio.gwp.backend.persistence.contents.entity.Contents
import com.kidaristudio.gwp.backend.persistence.contents.repository.ContentsRepository
import org.springframework.stereotype.Service

@Service
class ContentsDomainService (
    val contentsRepository: ContentsRepository,
) {
    suspend fun getById(id: Long): Contents? {
        return contentsRepository.findById(id)
    }
}
