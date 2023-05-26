package com.dicoding.c23ps051.caferecommenderapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.work_sans)),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
    ),
)