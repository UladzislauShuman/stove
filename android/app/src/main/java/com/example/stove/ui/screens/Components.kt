package com.example.stove.ui.screens

import android.media.Image
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.stove.R

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
fun IconWithBackground(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(dimensionResource(R.dimen.background_icon_size))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size))

            )
        }
    }
}


// New from the server, need API now
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


@Composable
fun DesignerOptionCard(
    title: String,
    imageRes: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Card (
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor =
                if(isSelected) MaterialTheme.colorScheme.secondaryContainer
                else MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.card_elevation)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
//                .size(dimensionResource(R.dimen.card_size))
                    .padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
