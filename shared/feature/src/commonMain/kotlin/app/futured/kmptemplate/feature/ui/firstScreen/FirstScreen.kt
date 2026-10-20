package app.futured.kmptemplate.feature.ui.firstScreen

import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions

    interface Actions {
        fun onPickMemo()
        fun onPickMemoBuilder()
    }
}
