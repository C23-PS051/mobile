package com.dicoding.c23ps051.caferecommenderapp.ui.event

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}
