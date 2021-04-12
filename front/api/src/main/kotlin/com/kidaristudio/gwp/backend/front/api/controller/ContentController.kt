package com.kidaristudio.gwp.backend.front.api.controller

import com.kidaristudio.gwp.backend.front.core.exception.BadRequestException
import com.kidaristudio.gwp.backend.front.core.model.response.contents.ContentsResponse
import com.kidaristudio.gwp.backend.front.core.model.response.contents.toResponse
import com.kidaristudio.gwp.backend.front.service.contents.ContentsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *  서비스별 API 컨트롤러
 * */
@Tag(name = "contents", description = "콘텐츠 API")
@RestController
@RequestMapping("/contents")
class ContentController(
    val contentsService: ContentsService
) {

    @GetMapping("/{contentsId}")
    @Operation(summary = "콘텐츠 조회", description = "id를 이용하여 contents 레코드를 조회합니다.")
    @ApiResponses(
       value = [
            ApiResponse(
                responseCode = "200",
                description = "회원 조회 성공",
                content = [Content( array= ( ArraySchema(schema = Schema(implementation = ContentsResponse::class))))]),
           ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
           ApiResponse(responseCode = "404", description = "Did not find anything", content = [Content()])]
        )
    suspend fun get(
        @Parameter(name = "id", description = "contents의 id", `in` = ParameterIn.PATH) @PathVariable contentsId: Long
    ): ContentsResponse? =
        contentsService.get(contentsId)?.toResponse()
}
