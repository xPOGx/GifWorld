package com.example.gifworld.ui.tools

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.gifworld.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    tryAgainAction: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.error_occurred),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(dimensionResource(R.dimen.medium))
            )
            Button(onClick = tryAgainAction) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}