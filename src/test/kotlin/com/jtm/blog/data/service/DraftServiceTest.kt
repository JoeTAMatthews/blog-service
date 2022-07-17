package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.repository.DraftRepository
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class DraftServiceTest {

    private val draftRepository: DraftRepository = mock()
    private val draftService = DraftService(draftRepository)

    fun addDraft_shouldThrowFound_whenSearching() {}

    fun addDraft_shouldSucceed() {}

    fun updateDraft_shouldThrowNotFound_whenSearching() {}

    fun updateDraft_shouldSucceed() {}

    fun getDraft_shouldThrowNotFound_whenSearching() {}

    fun getDraft_shouldSucceed() {}

    fun getDrafts() {}

    fun removeDraft_shouldThrowNotFound_whenSearching() {}

    fun removeDraft_shouldSucceed() {}
}