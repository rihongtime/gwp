package com.kidaristudio.gwp.backend.front.service.contents

import com.kidaristudio.gwp.backend.persistence.common.logger.Logger
import com.kidaristudio.gwp.backend.persistence.contents.entity.Contents
import com.kidaristudio.gwp.backend.persistence.contents.service.ContentsDomainService
import org.springframework.stereotype.Service

@Service
class ContentsService(
    private val contentsDomainService: ContentsDomainService
) {

    private val logger by Logger()

    suspend fun get(contentsId: Long): Contents? {
        return contentsDomainService.getById(contentsId)
    }
}