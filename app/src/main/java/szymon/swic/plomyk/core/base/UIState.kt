package szymon.swic.plomyk.core.base

sealed class UIState {
    object Idle : UIState()
    object Pending : UIState()
}
