package com.example.gifworld.ui.tools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.example.gifworld.R

@Composable
fun FloatingButtonsRow(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    banGif: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(dimensionResource(R.dimen.between_grid_elements))
            .fillMaxWidth()
    ) {
        FloatingButton(
            onClick = navigateUp,
            imageVector = Icons.Default.ArrowBack,
        )
        FloatingButton(
            onClick = banGif,
            imageVector = Icons.Default.Delete
        )
    }
}

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}