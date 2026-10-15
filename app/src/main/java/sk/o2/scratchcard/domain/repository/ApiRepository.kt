package sk.o2.scratchcard.domain.repository

import sk.o2.scratchcard.domain.model.ActivationResult

interface ApiRepository {
    suspend fun activateCode(code: String): ActivationResult
}
