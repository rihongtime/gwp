package com.kidaristudio.gwp.backend.front.api.controller

import com.kidaristudio.gwp.backend.front.core.model.response.contents.ContentsResponse
import com.kidaristudio.gwp.backend.front.core.model.response.contents.toResponse
import com.kidaristudio.gwp.backend.front.service.contents.ContentsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *  서비스별 API 컨트롤러
 * */
@RestController
@RequestMapping("/contents")
class ContentController(
    val contentsService: ContentsService
) {

    @GetMapping("/{contentsId}")
    suspend fun get(
        @PathVariable contentsId: Long
    ): ContentsResponse? =
        contentsService.get(contentsId)?.toResponse()
}