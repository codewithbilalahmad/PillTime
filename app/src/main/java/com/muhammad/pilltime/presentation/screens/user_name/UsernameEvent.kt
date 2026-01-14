package com.muhammad.pilltime.presentation.screens.user_name

sealed interface UsernameEvent{
    data object OnSaveUsernameDone : UsernameEvent
}