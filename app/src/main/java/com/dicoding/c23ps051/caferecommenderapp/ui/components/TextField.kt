package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.White

private val textFieldHeight = 60.dp

@Composable
fun InputTextField(label: String) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().height(textFieldHeight)
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }
    var hasError by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().height(textFieldHeight),
        value = text,
        onValueChange = { newText: String ->
            text = newText
            val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
            hasError = !emailRegex.matches(text)
        },
        label = { Text(stringResource(id =R.string.email)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        isError = hasError,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (hasError) Red else Gray,
            unfocusedBorderColor = if (hasError) Red else Gray,
            textColor = if (hasError) Red else Gray,
            cursorColor = if (hasError) Red else Gray,
        )
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    var hasError by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().height(textFieldHeight),
        value = text,
        onValueChange = { newText: String ->
            text = newText
            val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
            hasError = !(passwordRegex.matches(text) || text.length > 8)
        },
        label = { Text(stringResource(id =R.string.password)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        isError = hasError,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    icon,
                    contentDescription = "Visibility",
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (hasError) Red else Gray,
            unfocusedBorderColor = if (hasError) Red else Gray,
            textColor = if (hasError) Red else Gray,
            cursorColor = if (hasError) Red else Gray,
        )
    )
}