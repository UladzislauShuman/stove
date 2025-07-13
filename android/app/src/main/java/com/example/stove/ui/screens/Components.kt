package com.example.stove.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    labelId: Int,
    isActiveButton: Boolean,
    textStyle: TextStyle,
    onClickBehavior: () -> Unit
) {
    Button(
        onClick = onClickBehavior,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            contentColor = if(isActiveButton) MaterialTheme.colorScheme.background
                    else MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = if(isActiveButton) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(
            text = stringResource(labelId),
            style = textStyle
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
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,

            //turning off bottom line
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Article(
    modifier: Modifier = Modifier,
    articleTitle: String,
    articleBody: String,
    articleImageUri: Uri
) {
    Row {
        Column{
            Text(
                text = articleTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = articleBody,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

    }
}

