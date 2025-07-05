package com.example.stove.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    labelId: Int,
    isActiveButton: Boolean,
    onClickBehavior: () -> Unit
) {
    Button(
        onClick = onClickBehavior,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = if(isActiveButton) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = if(isActiveButton) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(
            text = stringResource(labelId),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    labelId: Int,
    onValueChange: (String) -> Unit,
    value: String,

) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(labelId)) },
        textStyle = MaterialTheme.typography.labelSmall,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.secondaryContainer)
    )
}