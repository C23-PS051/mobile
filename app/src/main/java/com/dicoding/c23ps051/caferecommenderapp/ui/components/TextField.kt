package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Red

//private val textFieldHeight = 64.dp

@Composable
fun OutlinedFormTextField(
    modifier: Modifier = Modifier,
    label: String,
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
fun OutlinedDropDownTextField(
    modifier: Modifier = Modifier,
    text: String,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
    notOnFocus: Boolean,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape)
            .border(
                width = if (notOnFocus) 1.dp else 2.dp,
                color = if (notOnFocus) Gray else MaterialTheme.colors.primary,
                shape = shape,
            )
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 14.dp)
    ) {
        Text(
            text = text,
            color = Gray,
            fontWeight = if (notOnFocus) FontWeight.Normal else FontWeight.Bold,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = if (notOnFocus) painterResource(id = R.drawable.expand_more)
                else painterResource(id = R.drawable.expand_less),
            contentDescription = if (notOnFocus) stringResource(id = R.string.expand_more)
                else stringResource(id = R.string.expand_less),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedFormTextField(
        label = label,
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
    text: String,
    hasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedFormTextField(
        label = stringResource(id =R.string.email),
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    text: String,
    hasError: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedFormTextField(
        label = stringResource(id =R.string.password),
        text = text,
        hasError = hasError,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun ConfirmPasswordTextField(
    modifier: Modifier = Modifier,
    confirmText: String,
    hasError: Boolean,
    showPassword: Boolean,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    OutlinedFormTextField(
        label = stringResource(id = R.string.repassword),
        text = confirmText,
        hasError = hasError,
        onValueChange = onValueChange,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}