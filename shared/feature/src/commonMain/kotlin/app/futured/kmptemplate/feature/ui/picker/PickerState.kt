package app.futured.kmptemplate.feature.ui.picker

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PickerState(val isLoading: Boolean = false, val items: ImmutableList<StringDesc> = persistentListOf())
