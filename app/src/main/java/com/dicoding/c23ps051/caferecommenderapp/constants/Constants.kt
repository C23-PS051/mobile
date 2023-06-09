package com.dicoding.c23ps051.caferecommenderapp.constants

import com.dicoding.c23ps051.caferecommenderapp.R

const val MIN_PASSWORD_LENGTH = 8
const val NAME_REGEX = "^(?!\\s)(?=.*[a-zA-Z])[a-zA-Z0-9\\s]+\$"
const val EMAIL_REGEX = "^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$"
const val PASSWORD_REGEX = "^(?=.*[A-Za-z])[A-Za-z\\d](?!.*\\s).{8,}\$"
const val DEFAULT_PHOTO_URI = "android.resource://com.dicoding.c23ps051.caferecommenderapp/${R.drawable.profile}"