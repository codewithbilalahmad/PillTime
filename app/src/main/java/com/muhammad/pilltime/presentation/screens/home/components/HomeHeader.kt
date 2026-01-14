package com.muhammad.pilltime.presentation.screens.home.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.muhammad.pilltime.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeHeader(modifier: Modifier = Modifier, onAddMedicationScreen: () -> Unit, username : String) {
    val context = LocalContext.current
    val isNotificationPermissionGranted = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(MaterialTheme.colorScheme.error),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "${stringResource(R.string.hey)} $username",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onError
                        )
                    )
                    Text(
                        text = stringResource(R.string.greeting),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onError
                        )
                    )
                }
            }
            val notificationIcon =
                if (isNotificationPermissionGranted) R.drawable.ic_notification_on else R.drawable.ic_notification
            Icon(
                imageVector = ImageVector.vectorResource(notificationIcon),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onError
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.create_new_medication).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onError
                    )
                )
                Button(
                    onClick = onAddMedicationScreen,
                    shapes = ButtonDefaults.shapes(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onError,
                        contentColor = Color.Black
                    ), contentPadding = PaddingValues(horizontal = 24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.start),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Image(
                painter = painterResource(R.drawable.medicine_background),
                contentDescription = null,
                modifier = Modifier
                    .size(220.dp)
                    .offset(x = 36.dp)
            )
        }
    }
}