package com.example.cyclistance.feature_mapping.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cyclistance.feature_authentication.presentation.theme.BackgroundColor
import com.example.cyclistance.feature_authentication.presentation.theme.ThemeColor


@Composable
fun ButtonDialogArea(
    modifier: Modifier,
    onClickCancelButton: () -> Unit,
    onClickConfirmButton: () -> Unit) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(
                onClick = onClickCancelButton,
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(0.5f)
                    .align(Alignment.CenterVertically)
                    .padding(all = 7.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BackgroundColor),
                shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center)
            }


            Button(
                onClick = onClickConfirmButton,
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(0.5f)
                    .align(Alignment.CenterVertically)
                    .padding(all = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ThemeColor),
                shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = "Confirm",
                    color = Color.Black,
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center)
            }


        }
    }
}