package com.kidaristudio.gwp.backend.persistence.contents.repository

import com.kidaristudio.gwp.backend.persistence.contents.entity.Contents
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentsRepository : CoroutineCrudRepository<Contents, Long> {
}