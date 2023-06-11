package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedFormTextField(
    label: String,
    text: String,
    hasError: Boolean,
    enableErrorCheck: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Gray,
        unfocusedBorderColor = Gray,
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = Gray,
        errorTextColor = Red,
        cursorColor = MaterialTheme.colorScheme.onBackground,
        unfocusedLabelColor = Gray,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTrailingIconColor = Gray,
    ),
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        isError = if (enableErrorCheck) hasError else false,
        colors = colors,
        shape = shape,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledOutlinedFormTextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hasError: Boolean = false,
    singleLine: Boolean = true,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val hasFocus = remember { mutableStateOf(false) }
    Column {
        Text(
            text = label,
            color = if (enabled) Color.Unspecified else MaterialTheme.colorScheme.outlineVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        BasicTextField(
            modifier = modifier
                .onFocusChanged { focusState ->
                    hasFocus.value = focusState.isFocused
                }
                .fillMaxWidth()
                .height(48.dp)
                .clip(shape)
                .border(
                    width = if (hasFocus.value) 2.dp else 1.dp,
                    color = if (enabled) {
                        if (hasError) Red else MaterialTheme.colorScheme.onBackground
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    },
                    shape = shape
                ),
            enabled = enabled,
            value = text,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    if (hasError) Red else Color.Unspecified
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            ),
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            cursorBrush = if (hasError) SolidColor(Red) else SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = text,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    trailingIcon = trailingIcon,
                    isError = hasError,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )
            }
        )
    }
}

@Composable
fun PasswordLabeledOutlinedTextField(
    enabled: Boolean,
    label: String,
    text: String,
    showPassword: Boolean,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
) {
    LabeledOutlinedFormTextField(
        modifier = modifier,
        enabled = enabled,
        label = label,
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(
                onClick = onTrailingIconClick,
                enabled = enabled,
            ) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.visibility),
                    tint = if (enabled) {
                        if (hasError) Red else MaterialTheme.colorScheme.onBackground
                    } else MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
    )
}

@Composable
fun OutlinedDropDownTextField(
    text: String,
    onClick: () -> Unit,
    notOnFocus: Boolean,
    verticalPadding: Int,
    horizontalPadding: Int,
    fontSize: Int,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(shape)
            .border(
                width = if (notOnFocus) 1.dp else 2.dp,
                color = if (notOnFocus) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                shape = shape,
            )
            .clickable { onClick() }
            .padding(vertical = verticalPadding.dp, horizontal = horizontalPadding.dp)
    ) {
        Text(
            text = text,
            color = if (notOnFocus) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
            fontWeight = if (notOnFocus) FontWeight.Normal else FontWeight.Bold,
            fontSize = fontSize.sp,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = if (notOnFocus) painterResource(id = R.drawable.expand_more)
                else painterResource(id = R.drawable.expand_less),
            contentDescription = if (notOnFocus) stringResource(id = R.string.expand_more)
                else stringResource(id = R.string.expand_less),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun InputTextField(
    label: String,
    text: String,
    hasError: Boolean,
    enableErrorCheck: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
) {
    OutlinedFormTextField(
        modifier = modifier,
        label = label,
        text = text,
        hasError = hasError,
        enableErrorCheck = enableErrorCheck,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun EmailTextField(
    text: String,
    hasError: Boolean,
    enableErrorCheck: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
) {
    OutlinedFormTextField(
        modifier = modifier,
        label = stringResource(id =R.string.email),
        text = text,
        hasError = hasError,
        enableErrorCheck = enableErrorCheck,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun PasswordTextField(
    text: String,
    hasError: Boolean,
    enableErrorCheck: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedFormTextField(
        modifier = modifier,
        label = stringResource(id =R.string.password),
        text = text,
        hasError = hasError,
        enableErrorCheck = enableErrorCheck,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { onClick() }) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.visibility),
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun ConfirmPasswordTextField(
    confirmText: String,
    hasError: Boolean,
    enableErrorCheck: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedFormTextField(
        modifier = modifier,
        label = stringResource(id = R.string.repassword),
        text = confirmText,
        hasError = hasError,
        enableErrorCheck = enableErrorCheck,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword) {
                painterResource(id = R.drawable.visibility)
            } else {
                painterResource(id = R.drawable.visibility_off)
            }

            IconButton(onClick = { onClick() }) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.visibility),
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}