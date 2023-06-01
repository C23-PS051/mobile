package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import kotlinx.coroutines.delay

@Composable
fun BackPressHandler(
    backPressState: BackPress,
    toggleBackPressState: () -> Unit,
    onIdleBackPress: () -> Unit,
) {
    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2000)
            toggleBackPressState()
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        toggleBackPressState()
        onIdleBackPress()
    }
}