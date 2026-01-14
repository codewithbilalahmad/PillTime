package com.muhammad.pilltime.presentation.screens.user_name

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.AppTextField
import com.muhammad.pilltime.presentation.components.DashedHorizontalDivider
import com.muhammad.pilltime.presentation.components.PrimaryButton
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.utils.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UsernameScreen(
    navHostController: NavHostController,
    viewModel: UsernameViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events, onEvent = {event ->
        when(event){
            UsernameEvent.OnSaveUsernameDone -> {
                navHostController.navigate(Destinations.HomeScreen)
            }
        }
    })
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()){
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.username))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        ), shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier.fillMaxWidth()
            )
            DashedHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryButton(
                text = stringResource(R.string.done),
                onClick = {
                    viewModel.onAction(UsernameAction.OnSaveUsername)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.username.isNotEmpty(),
                contentPadding = PaddingValues(vertical = 16.dp)
            )
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.family),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.what_is_your_name),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(value = state.username, onValueChange = { newValue ->
                viewModel.onAction(UsernameAction.OnUsernameChange(newValue))
            }, modifier = Modifier.fillMaxWidth(), hint = R.string.username)
        }
    }
}