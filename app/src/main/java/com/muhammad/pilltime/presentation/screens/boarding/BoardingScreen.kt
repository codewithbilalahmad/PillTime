package com.muhammad.pilltime.presentation.screens.boarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.PagerDotIndicator
import com.muhammad.pilltime.presentation.components.PrimaryButton
import com.muhammad.pilltime.presentation.components.SecondaryButton
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.utils.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoardingScreen(
    navHostController: NavHostController,
    viewModel: BoardingViewModel = koinViewModel(),
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { state.boardingItems.size }
    val currentPage by remember {
        derivedStateOf { pagerState.currentPage }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .animateContentSize(MaterialTheme.motionScheme.slowEffectsSpec())
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (currentPage > 0 && currentPage < pagerState.pageCount - 1) {
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.background,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentPage - 1)
                            }
                        },
                        contentPadding = PaddingValues(vertical = 16.dp),
                        text = stringResource(R.string.previous)
                    )
                }
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (currentPage < pagerState.pageCount - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(currentPage + 1)
                            }
                        } else {
                            navHostController.navigate(Destinations.UsernameScreen)
                        }
                    },
                    contentPadding = PaddingValues(vertical = 16.dp),
                    text = if (currentPage < pagerState.pageCount - 1) stringResource(R.string.next) else stringResource(
                        R.string.start
                    )
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(paddingValues)
        ) { page ->
            val currentBoarding = state.boardingItems[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = pagerState.currentPageOffsetFraction
                        translationX = size.width * pageOffset
                    }
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(currentBoarding.image),
                        contentDescription = null, modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(currentBoarding.title),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(currentBoarding.description),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.surface,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    PagerDotIndicator(pagerState = pagerState)
                    Spacer(Modifier.height(32.dp))
                }
                AnimatedVisibility(
                    visible = currentPage < pagerState.pageCount - 1,
                    enter = scaleIn(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) + fadeIn(
                        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                    ),
                    exit = scaleOut(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) + fadeOut(
                        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                    ), modifier = Modifier
                        .padding(end = 24.dp, top = 12.dp)
                        .align(Alignment.TopEnd)
                ) {
                    PrimaryButton(
                        onClick = {
                            navHostController.navigate(Destinations.UsernameScreen)
                        },
                        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
                        text = stringResource(R.string.skip)
                    )
                }
            }
        }
    }
}