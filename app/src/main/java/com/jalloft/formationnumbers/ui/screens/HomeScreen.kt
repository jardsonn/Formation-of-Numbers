package com.jalloft.formationnumbers.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import com.jalloft.formationnumbers.R

import com.jalloft.formationnumbers.ui.theme.Background
import com.jalloft.formationnumbers.ui.theme.No
import com.jalloft.formationnumbers.ui.theme.Primary
import com.jalloft.formationnumbers.ui.theme.Yes
import com.jalloft.formationnumbers.ui.utils.TABLES




@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.formation_of_numbers),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp
        )

        var tableCount by remember {
            mutableStateOf(0)
        }
        var chosenNumber by remember {
            mutableStateOf(0)
        }

        val showResult = tableCount > 5

        val numbers = TABLES[minOf(tableCount, 5)]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {

            AnimatedVisibility(
                visible = showResult,
                enter = slideInVertically(initialOffsetY = { -it / 4 }),
                exit = slideOutVertically(targetOffsetY = { -it / 4 })
            ) {
                TextChosenNumber(chosenNumber, modifier = Modifier.padding(0.dp))
            }

            AnimatedVisibility(
                visible = !showResult,
                enter = slideInVertically(initialOffsetY = { it / 4 }),
                exit = slideOutVertically(targetOffsetY = { it / 4 })
            ) {
                Table(modifier = Modifier.padding(0.dp), minOf(tableCount, 5), numbers)
            }
        }

        if (!showResult) {
            Controls(yesClick = {
                tableCount++
                chosenNumber += numbers.min()
            }, noClick = {
                tableCount++
            })
        } else {
            ControlButton(
                onClick = {
                    chosenNumber = 0
                    tableCount = 0
                },
                text = stringResource(id = R.string.restart).uppercase(),
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TextChosenNumber(chosenNumber: Int, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.result_message),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .padding(78.dp)
                .drawBehind {
                    drawCircle(
                        color = Primary,
                        radius = this.size.maxDimension
                    )
                },
            text = chosenNumber.toString(),
            color = MaterialTheme.colorScheme.background,
            textAlign = TextAlign.Center,
            fontSize = 100.sp,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black
        )

    }

}

@Composable
fun Controls(yesClick: () -> Unit, noClick: () -> Unit) {
    Text(
        text = stringResource(R.string.question_if_the_number_is),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Center,
        fontSize = 19.sp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ControlButton(
            onClick = noClick,
            text = stringResource(id = R.string.no).uppercase(),
            modifier = Modifier.weight(1f),
            containerColor = No
        )
        ControlButton(
            onClick = yesClick,
            text = stringResource(id = R.string.yes).uppercase(),
            modifier = Modifier.weight(1f),
            containerColor = Yes
        )

    }
}

@Composable
fun ControlButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Background
        )
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
fun Table(modifier: Modifier = Modifier, tableCount: Int, numbers: Array<Int>) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tabela ${tableCount + 1}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 30.sp
        )

        LazyVerticalGrid(
            modifier = Modifier
                // .fillMaxSize()
                .padding(bottom = 16.dp),
            columns = GridCells.Fixed(8),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            contentPadding = PaddingValues(8.dp)
        ) {

            items(numbers) {
                Text(
                    text = it.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(50.dp)
                        )
                        .padding(6.dp)
                )
            }
        }
    }

}


private operator fun Float.plus(textUnit: TextUnit): Float =
    this * textUnit.value

