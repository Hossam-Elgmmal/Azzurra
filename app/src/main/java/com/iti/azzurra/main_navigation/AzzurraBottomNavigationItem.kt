package com.iti.azzurra.main_navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.azzurra.R
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun AzzurraBottomNavigationItem(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    label: Int,
) {
    val sizeAnimationProgress: Float by
        animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
        )
    val colorAnimationProgress: Float by
        animateFloatAsState(
            targetValue = if (selected) 0.5f else 0f,
            animationSpec = MaterialTheme.motionScheme.defaultEffectsSpec(),
        )

    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
        animationSpec =  MaterialTheme.motionScheme.defaultEffectsSpec(),
    )
    Box(
        modifier = modifier
            .width(80.dp)
            .height(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize(sizeAnimationProgress)
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = colorAnimationProgress), CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                tint = contentColor
            )
            Text(
                text = stringResource(label),
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun AzzurraBottomNavigationItemPreview() {
    AzzurraTheme {
        AzzurraBottomNavigationItem(
            onClick = {},
            icon = R.drawable.ic_alert_filled,
            modifier = Modifier,
            selected = true,
            label = R.string.alerts
        )
    }
}