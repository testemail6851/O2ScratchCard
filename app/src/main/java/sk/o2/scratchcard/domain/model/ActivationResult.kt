package sk.o2.scratchcard.domain.model

sealed interface ActivationResult {
    data object Success : ActivationResult
    data object NoInternet : ActivationResult
    data class ApiError(val message: String?) : ActivationResult
    data class InvalidCode(val message: String?) : ActivationResult
}
