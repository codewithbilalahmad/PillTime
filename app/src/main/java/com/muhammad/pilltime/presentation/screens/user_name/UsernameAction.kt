package com.muhammad.pilltime.presentation.screens.user_name

sealed interface UsernameAction{
    data class OnUsernameChange(val name : String) : UsernameAction
    data object OnSaveUsername : UsernameAction
}