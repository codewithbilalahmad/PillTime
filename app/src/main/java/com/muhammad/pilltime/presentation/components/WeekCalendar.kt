package com.muhammad.pilltime.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import com.muhammad.pilltime.R
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.utils.getWeekDatesFrom
import com.muhammad.pilltime.utils.rippleClickable
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WeekCalendar(
    modifier: Modifier = Modifier,
    weekRange: IntRange = -100..100,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val pagerState = rememberPagerState(initialPage = weekRange.count() / 2) { weekRange.count() }
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }
    val currentWeekStart =
        initialDate.plus((currentPage - weekRange.count() / 2), DateTimeUnit.WEEK)
    val visibleWeekDates = getWeekDatesFrom(currentWeekStart)
    val visibleMonthName =
        visibleWeekDates[3].month.name.lowercase().replaceFirstChar { it.uppercase() }
    val visibleYear = visibleWeekDates[3].year
    val month = when (visibleMonthName) {
        "January" -> stringResource(R.string.january)
        "February" -> stringResource(R.string.february)
        "March" -> stringResource(R.string.march)
        "April" -> stringResource(R.string.april)
        "May" -> stringResource(R.string.may)
        "June" -> stringResource(R.string.june)
        "July" -> stringResource(R.string.july)
        "August" -> stringResource(R.string.august)
        "September" -> stringResource(R.string.september)
        "October" -> stringResource(R.string.october)
        "November" -> stringResource(R.string.november)
        else -> stringResource(R.string.december)
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = MaterialTheme.motionScheme.fastEffectsSpec()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedContent(targetState = "$month $visibleYear", transitionSpec = {
                (slideInVertically(initialOffsetY = { it }) + expandVertically(initialHeight = { it }) + fadeIn()) togetherWith (slideOutVertically(
                    targetOffsetY = { -it }) + shrinkVertically(targetHeight = { -it }) + fadeOut())
            }, label = "monthYearAnimation") { monthYear ->
                Text(
                    text = monthYear,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }, shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_backward),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }, shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_forward),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            modifier = Modifier.fillMaxWidth(),
            key = { it }) { page ->
            val weekStart = initialDate.plus((page - (weekRange.count() / 2)), DateTimeUnit.WEEK)
            val dates = getWeekDatesFrom(weekStart)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                dates.forEach { date ->
                    val isSelected = date == selectedDate
                    val isToday = date == today
                    val borderColor by animateColorAsState(
                        targetValue = when {
                            isSelected -> MaterialTheme.colorScheme.primary
                            isToday -> MaterialTheme.colorScheme.surface
                            else -> Color.Transparent
                        },
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "borderColor"
                    )
                    val borderWidth by animateDpAsState(
                        targetValue = if (isSelected || isToday) 2.dp else 0.dp,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "borderWidth"
                    )
                    val labelColor by animateColorAsState(
                        targetValue = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "contentColor"
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "contentColor"
                    )
                    val name =
                        date.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
                    val day = when (name) {
                        "Mon" -> stringResource(R.string.monday)
                        "Tue" -> stringResource(R.string.tuesday)
                        "Wed" -> stringResource(R.string.wednesday)
                        "Thu" -> stringResource(R.string.thursday)
                        "Fri" -> stringResource(R.string.friday)
                        "Sat" -> stringResource(R.string.saturday)
                        else -> stringResource(R.string.sunday)
                    }
                    val number = date.day
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = labelColor,
                                textAlign = TextAlign.Center
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(
                                    width = borderWidth,
                                    color = borderColor,
                                    shape = CircleShape
                                )
                                .rippleClickable {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                                    onDateSelected(date)
                                }, contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = number.toString(),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = contentColor,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}