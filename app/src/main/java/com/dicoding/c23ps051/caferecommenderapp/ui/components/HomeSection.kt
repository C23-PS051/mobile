package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.c23ps051.caferecommenderapp.ui.CafeList

@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        SectionText(title, modifier)
        CafeList()
        Spacer(modifier = Modifier.height(16.dp))
    }
}