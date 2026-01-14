package com.muhammad.pilltime.presentation.screens.user_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.domain.repository.SettingPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsernameViewModel(
    private val settingPreferences: SettingPreferences
) : ViewModel(){
    private val _state= MutableStateFlow(UsernameState())
    val state = _state.asStateFlow()
    private val _events = Channel<UsernameEvent>()
    val events = _events.receiveAsFlow()
    fun onAction(action : UsernameAction){
        when(action){
            UsernameAction.OnSaveUsername -> onSaveUsername()
            is UsernameAction.OnUsernameChange -> onUsernameChange(action.name)
        }
    }

    private fun onUsernameChange(name: String) {
        _state.update { it.copy(username = name) }
    }

    private fun onSaveUsername() {
        viewModelScope.launch {
            settingPreferences.saveUsername(state.value.username)
            settingPreferences.saveShowBoarding(show = false)
            _events.trySend(UsernameEvent.OnSaveUsernameDone)
        }
    }
}