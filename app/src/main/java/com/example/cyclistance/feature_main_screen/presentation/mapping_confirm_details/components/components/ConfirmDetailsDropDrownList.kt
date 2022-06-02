package com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details.components.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cyclistance.theme.Black500


private val bikeList = listOf(
    "Road Bike",
    "Mountain Bike",
    "Fat Bike",
    "Touring Bike",
    "Fixed Gear/ Track Bike",
    "BMX",
    "Japanese Bike")

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownBikeList(modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }


    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {

        TextField(
            modifier = Modifier
                .shadow(15.dp, shape = RoundedCornerShape(12.dp), clip = true)
                .fillMaxWidth()
                .wrapContentHeight(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            placeholder = {
                Text(
                    "Bike Type",
                    color = Black500,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.PedalBike,
                    contentDescription = "Bike Icon",
                    tint = Black500,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary,
                trailingIconColor = Black500

            ),
        )


        ExposedDropdownMenu(modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .fillMaxWidth()
            .wrapContentHeight(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {


            bikeList.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = selectionOption,
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.subtitle2)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropDown() {
    DropDownBikeList(modifier = Modifier
        .shadow(2.dp, shape = RoundedCornerShape(12.dp), clip = true))
}