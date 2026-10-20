package app.futured.kmptemplate.android.ui.components

enum class MemoryDifficulty(val pairsCount: Int) {
    EASY(5),
    MEDIUM(10),
    HARD(15),
    ;

    companion object {
        fun from(value: String): MemoryDifficulty = when (value.lowercase()) {
            "средне" -> MEDIUM
            "сложно" -> HARD
            else -> EASY //
        }
    }
}

enum class CardState { CLOSED, OPEN, MATCHED }

data class MemoryCard(
    val id: Int,
    val color: Long,
    val state: CardState = CardState.CLOSED,
)

//TODO: switch from colors to pictures
val memoryColors: List<Long> = listOf(
    0xFFE57373, 0xFFF06292, 0xFFBA68C8, 0xFF9575CD, 0xFF7986CB,
    0xFF64B5F6, 0xFF4FC3F7, 0xFF4DD0E1, 0xFF4DB6AC, 0xFF81C784,
    0xFFAED581, 0xFFFFD54F, 0xFFFFB74D, 0xFFFF8A65, 0xFFA1887F,
)
