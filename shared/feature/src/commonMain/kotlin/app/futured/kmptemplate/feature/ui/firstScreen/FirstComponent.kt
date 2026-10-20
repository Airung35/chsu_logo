package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.annotation.GenerateFactory
import app.futured.arkitekt.decompose.navigation.resultFlow
import app.futured.kmptemplate.feature.navigation.result.NavigationResultKeys
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.picker.PickerArgs
import app.futured.kmptemplate.feature.ui.picker.PickerType
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
@GenerateFactory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FirstScreenNavigation,
) : ScreenComponent<FirstViewState, Nothing, FirstScreenNavigation>(
    componentContext = componentContext,
    defaultState = FirstViewState(),
),
    FirstScreen,
    FirstScreenNavigation by navigation,
    FirstScreen.Actions {

    override val viewState: StateFlow<FirstViewState> = componentState
    override val actions: FirstScreen.Actions = this
    private val pickerResults = resultFlow(NavigationResultKeys.Picker)


    override fun onPickMemo() = openPicker(PickerType.Difficulty, PickerArgs(NavigationResultKeys.Picker))

    override fun onPickMemoBuilder() = navigateToThird("hello third")

    init {
        lifecycle.doOnCreate {
            collectPickerResults()
        }
    }
    private fun collectPickerResults() {
        pickerResults
            .onEach { selection -> navigateToSecond() }
            .launchIn(lifecycleScope)
    }
}
