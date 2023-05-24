package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red

//private val textFieldHeight = 64.dp

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
//    focusManager: FocusManager,
    text: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = if (hasError) Red else Gray,
        unfocusedBorderColor = if (hasError) Red else Gray,
        textColor = if (hasError) Red else Gray,
        cursorColor = if (hasError) Red else Gray,
        unfocusedLabelColor = if (hasError) Red else Gray,
    ),
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        isError = hasError,
        colors = colors,
        shape = shape,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
    )
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    label: String,
//    focusManager: FocusManager,
    text: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedTextField(
        label = label,
//        focusManager = focusManager,
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
//    focusManager: FocusManager,
    text: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedTextField(
        label = stringResource(id =R.string.email),
//        focusManager = focusManager,
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

//@Composable
//fun EmailTextField(
//    modifier: Modifier = Modifier,
//) {
//    val focusManager = LocalFocusManager.current
//    var text by rememberSaveable { mutableStateOf("") }
//    var hasError by rememberSaveable { mutableStateOf(false) }
//    OutlinedTextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(textFieldHeight),
//        value = text,
//        onValueChange = { newText: String ->
//            text = newText
//            val emailRegex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
//            hasError = !emailRegex.matches(text)
//        },
//        label = { Text(stringResource(id =R.string.email)) },
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Email,
//            imeAction = ImeAction.Done,
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                focusManager.clearFocus()
//            }
//        ),
//        singleLine = true,
//        isError = hasError,
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = if (hasError) Red else Gray,
//            unfocusedBorderColor = if (hasError) Red else Gray,
//            textColor = if (hasError) Red else Gray,
//            cursorColor = if (hasError) Red else Gray,
//        ),
//        shape = MaterialTheme.shapes.large,
//    )
//}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
//    focusManager: FocusManager,
    text: String,
    hasError: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedTextField(
        label = stringResource(id =R.string.password),
//        focusManager = focusManager,
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

//@Composable
//fun PasswordTextField(
//    modifier: Modifier = Modifier,
//) {
//    val focusManager = LocalFocusManager.current
//    var showPassword by rememberSaveable { mutableStateOf(false) }
//    var text by rememberSaveable { mutableStateOf("") }
//    var hasError by rememberSaveable { mutableStateOf(false) }
//    OutlinedTextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(textFieldHeight),
//        value = text,
//        onValueChange = { newText: String ->
//            text = newText
//            hasError = text.length < 8
//        },
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.Done,
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                focusManager.clearFocus()
//            }
//        ),
//        label = { Text(stringResource(id =R.string.password)) },
//        singleLine = true,
//        isError = hasError,
//        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = {
//            val icon = if (showPassword) {
//                painterResource(id = R.drawable.visibility)
//            } else {
//                painterResource(id = R.drawable.visibility_off)
//            }
//
//            IconButton(onClick = { showPassword = !showPassword }) {
//                Icon(
//                    icon,
//                    contentDescription = "Visibility",
//                )
//            }
//        },
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = if (hasError) Red else Gray,
//            unfocusedBorderColor = if (hasError) Red else Gray,
//            textColor = if (hasError) Red else Gray,
//            cursorColor = if (hasError) Red else Gray,
//        ),
//        shape = MaterialTheme.shapes.large,
//    )
//}

@Composable
fun ConfirmPasswordTextField(
    modifier: Modifier = Modifier,
//    focusManager: FocusManager,
    confirmText: String,
    hasError: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedTextField(
        label = stringResource(id = R.string.repassword),
//        focusManager = focusManager,
        text = confirmText,
        hasError = hasError,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}


//@Composable
//fun ConfirmPasswordTextField(
//    text: String,
//    modifier: Modifier = Modifier,
//) {
//    val focusManager = LocalFocusManager.current
//    var showPassword by rememberSaveable { mutableStateOf(false) }
//    var confirmText by rememberSaveable { mutableStateOf("") }
//    var hasError by rememberSaveable { mutableStateOf(false) }
//    OutlinedTextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(textFieldHeight),
//        value = confirmText,
//        onValueChange = { newText: String ->
//            confirmText = newText
//            hasError = !(text == confirmText || text.length >= 8)
//        },
//        label = { Text(stringResource(id =R.string.repassword)) },
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.Done,
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                focusManager.clearFocus()
//            }
//        ),
//        singleLine = true,
//        isError = hasError,
//        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = {
//            val icon = if (showPassword) {
//                painterResource(id = R.drawable.visibility)
//            } else {
//                painterResource(id = R.drawable.visibility_off)
//            }
//
//            IconButton(onClick = { showPassword = !showPassword }) {
//                Icon(
//                    icon,
//                    contentDescription = "Visibility",
//                )
//            }
//        },
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = if (hasError) Red else Gray,
//            unfocusedBorderColor = if (hasError) Red else Gray,
//            textColor = if (hasError) Red else Gray,
//            cursorColor = if (hasError) Red else Gray,
//        ),
//        shape = MaterialTheme.shapes.large,
//    )
//}