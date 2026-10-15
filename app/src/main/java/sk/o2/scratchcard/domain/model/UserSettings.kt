package sk.o2.scratchcard.domain.model

import sk.o2.scratchcard.data.local.ScratchStatus
import timber.log.Timber

data class UserSettings(
    val code: String? = null,
    val isCodeActivated: Boolean = false
) {
    fun getScratchStatus(): ScratchStatus {
        Timber.d("getScratchStatus: $this -> isCodeActivated: $isCodeActivated, code: $code")
        return when {
            isCodeActivated -> ScratchStatus.Activated
            code != null -> ScratchStatus.Scratched
            else -> ScratchStatus.Unscratched
        }
    }
}