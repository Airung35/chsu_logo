package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class SecondComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigation,
    @InjectedParam args: SecondScreenArgs,
) : ScreenComponent<SecondViewState, Nothing, SecondScreenNavigation>(
    componentContext = componentContext,
    defaultState = SecondViewState(difficulty = MemoryDifficulty.from(args.difficulty)),
),
    SecondScreen,
    SecondScreenNavigation by navigation,
    SecondScreen.Actions {

    override val viewState: StateFlow<SecondViewState> = componentState
    override val actions: SecondScreen.Actions = this

    init {
        startGame()
    }

    override fun onBack() = pop()

    override fun onCardClick(cardId: Int) {
        val state = componentState.value
        val card = state.cards.firstOrNull { it.id == cardId } ?: return

        if (card.state != CardState.CLOSED) return
        if (state.cards.count { it.state == CardState.OPEN } >= 2) return

        val openedCard = state.cards.firstOrNull { it.state == CardState.OPEN }
        setCardState(cardId, CardState.OPEN)

        if (openedCard != null) {
            componentState.update { it.copy(moves = it.moves + 1) }
            if (openedCard.color == card.color) {
                setCardState(openedCard.id, CardState.MATCHED)
                setCardState(card.id, CardState.MATCHED)
                checkGameFinished()
            } else {
                lifecycleScope.launch {
                    delay(FLIP_BACK_DELAY_MS)
                    setCardState(openedCard.id, CardState.CLOSED)
                    setCardState(card.id, CardState.CLOSED)
                }
            }
        }
    }

    private fun startGame() {
        val difficulty = componentState.value.difficulty
        val colors = memoryColors.shuffled().take(difficulty.pairsCount)
        val cards = (colors + colors) // дублируем = пары
            .shuffled()
            .mapIndexed { index, color -> MemoryCard(id = index, color = color) }
            .toImmutableList()
        componentState.update {
            it.copy(cards = cards, moves = 0, isGameFinished = false)
        }
    }

    private fun setCardState(cardId: Int, newState: CardState) {
        componentState.update { state ->
            state.copy(
                cards = state.cards
                    .map { if (it.id == cardId) it.copy(state = newState) else it }
                    .toImmutableList(),
            )
        }
    }

    private fun checkGameFinished() {
        if (componentState.value.cards.all { it.state == CardState.MATCHED }) {
            componentState.update { it.copy(isGameFinished = true) }
        }
    }

    private companion object {
        const val FLIP_BACK_DELAY_MS = 700L
    }
}
