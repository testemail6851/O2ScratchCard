package sk.o2.scratchcard.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class VersionDto(
    val android: String?
)