package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.picker.PickerArgs
import app.futured.kmptemplate.feature.ui.picker.PickerType

internal interface FirstScreenNavigation : NavigationActions {
    fun FirstComponent.navigateToSecond(difficulty: String)

    fun FirstComponent.navigateToThird(id: String)

    fun FirstComponent.openPicker(type: PickerType, args: PickerArgs)
}
