package com.iti.azzurra.features.map.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.common.GradientIcon
import com.iti.azzurra.data.places.models.PlacePrediction
import com.iti.azzurra.ui.theme.AlmaraiFontFamily
import com.iti.azzurra.ui.theme.AzzurraTheme
import kotlin.collections.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MapTopBar(
    textFieldValue: TextFieldValue,
    isLoading: Boolean,
    predictions: List<PlacePrediction>,
    onQueryChanged: (TextFieldValue) -> Unit,
    onPredictionSelected: (PlacePrediction) -> Unit,
    onBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedCard(
                shape = CircleShape,
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.go_back)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(6.dp, RoundedCornerShape(28.dp)),
            ) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = onQueryChanged,
                    placeholder = {
                        Text(stringResource(R.string.search_for_city))
                    },
                    trailingIcon = {
                        if (textFieldValue.text.isNotEmpty()) {
                            IconButton(onClick = { onQueryChanged(TextFieldValue("")) }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                    contentDescription = stringResource(R.string.clear_search_text)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = AlmaraiFontFamily
                    ),
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                if (isLoading) {
                    LinearWavyProgressIndicator(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                    )
                }
            }
        }

        if (predictions.isNotEmpty()) {

            OutlinedCard (
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .heightIn(max = 320.dp)
            ) {

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {

                    items(
                        items = predictions,
                        key = { it.placeId }
                    ) { prediction ->

                        ListItem(
                            headlineContent = {
                                Text(
                                    text = prediction.primaryText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = prediction.secondaryText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            leadingContent = {
                                GradientIcon(
                                    iconId = R.drawable.ic_location
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    keyboardController?.hide()
                                    onPredictionSelected(prediction)
                                }
                                .padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MapTopBarPreview() {
    AzzurraTheme {
        MapTopBar(
            textFieldValue = TextFieldValue(""),
            isLoading = true,
            predictions = List(5) {
                PlacePrediction(
                    placeId = "placeId$it",
                    primaryText = "primaryText$it",
                    secondaryText = "secondaryText"
                )
            },
            onQueryChanged = {},
            onPredictionSelected = {},
            onBack = {}
        )
    }
}