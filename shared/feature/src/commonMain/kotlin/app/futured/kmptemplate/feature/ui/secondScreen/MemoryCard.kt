package app.futured.kmptemplate.feature.ui.secondScreen

enum class MemoryDifficulty(val pairsCount: Int) {
    EASY(5),
    MEDIUM(10),
    HARD(15),
    ;
    //TODO: remove hardcode
    companion object {
        fun from(value: String): MemoryDifficulty = when {
            value.equals("Medium", ignoreCase = true) ||
                value.equals("Средне", ignoreCase = true) -> MEDIUM
            value.equals("Hard", ignoreCase = true) ||
                value.equals("Сложно", ignoreCase = true) -> HARD
            else -> EASY
        }
    }
}

enum class CardState { CLOSED, OPEN, MATCHED }

data class MemoryCard(
    val id: Int,
    val color: Long, // ARGB
    val state: CardState = CardState.CLOSED,
)

//TODO: change from colors to pictures
val memoryColors: List<Long> = listOf(
    0xFFE57373, 0xFFF06292, 0xFFBA68C8, 0xFF9575CD, 0xFF7986CB,
    0xFF64B5F6, 0xFF4FC3F7, 0xFF4DD0E1, 0xFF4DB6AC, 0xFF81C784,
    0xFFAED581, 0xFFFFD54F, 0xFFFFB74D, 0xFFFF8A65, 0xFFA1887F,
)
