package com.example.revealswipelazycolumn

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor:androidx.compose.ui.graphics.Color,
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String? = null,
    tint: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.White
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}