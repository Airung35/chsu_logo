package app.futured.kmptemplate.feature.ui.secondScreen

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SecondViewState(
    val difficulty: MemoryDifficulty,
    val cards: ImmutableList<MemoryCard> = persistentListOf(),
    val moves: Int = 0,
    val isGameFinished: Boolean = false,
)
