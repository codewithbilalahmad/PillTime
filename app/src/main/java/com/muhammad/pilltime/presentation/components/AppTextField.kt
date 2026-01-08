package com.muhammad.pilltime.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    leadingIcon: Int? = null,
    onTrailingIconClick: () -> Unit = {},
    trailingIcon: Int? = null,
    onClick: () -> Unit = {},
    shape: Shape = CircleShape,
    @StringRes hint: Int? = null,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    trailingBackground: Color = MaterialTheme.colorScheme.primary,
    onKeyBoardAction: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        maxLines = 1,
        singleLine = true, enabled = enabled,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = shape
            )
            .clickable(onClick = onClick)
            .then(
                if (trailingIcon == null) Modifier.padding(16.dp) else Modifier.padding(
                    start = 16.dp,
                    top = 2.dp,
                    bottom = 2.dp
                )
            ),
        keyboardActions = KeyboardActions(onDone = {
            onKeyBoardAction()
        }),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(leadingIcon),
                        contentDescription = null, modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.surface
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty() && hint != null) {
                        Text(
                            text = stringResource(hint),
                            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.surface)
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                trailingBackground
                            )
                            .clickable(onClick = onTrailingIconClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(trailingIcon),
                            contentDescription = null, modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        })
}