package com.example.gifworld.ui.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gifworld.R
import com.example.gifworld.utils.Status

@Composable
fun PagerStatusBox(
    modifier: Modifier = Modifier,
    status: Status,
    tryAgain: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        when (status) {
            Status.Error -> {
                Text(text = stringResource(R.string.error_occurred))
                Button(onClick = tryAgain) {
                    Text(text = stringResource(R.string.try_again))
                }
            }

            else -> {
                Text(text = stringResource(R.string.loading_new_gifs))
                CircularProgressIndicator()
            }
        }
    }
}