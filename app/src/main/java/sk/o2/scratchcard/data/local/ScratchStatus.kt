package sk.o2.scratchcard.data.local

import androidx.annotation.StringRes
import sk.o2.scratchcard.R

sealed class ScratchStatus(
    val stepIndex: Int,
    @StringRes val textId: Int
) {
    data object Unscratched: ScratchStatus(stepIndex = 0, textId = R.string.status_unscratched)
    data object Scratched: ScratchStatus(stepIndex = 1, textId = R.string.status_scratched)
    data object Activated: ScratchStatus(stepIndex = 2, textId = R.string.status_activated)
}