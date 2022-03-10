package com.example.cyclistance.feature_mapping.presentation.components


import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.cyclistance.R
import com.example.cyclistance.feature_authentication.presentation.common.AnimatedImage
import com.example.cyclistance.feature_authentication.presentation.theme.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MappingBottomSheet(sheetContent: @Composable ColumnScope.() -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = sheetContent,
        sheetPeekHeight = 0.dp) {
/*
        Button(onClick = {
            scope.launch {

                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }) {
            Text(text = "Expand/Collapse Bottom Sheet")
        }
    }

 */
    }
}

@Composable
fun BottomSheetSearchingAssistance(onClickButton: () -> Unit) {

    MappingBottomSheet() {


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {


            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ShowableDisplaysBackgroundColor)) {

                val (searchAnimatedIcon, searchingText, cancelButton) = createRefs()



                AnimatedImage(
                    imageId = R.drawable.ic_magnifying_search,
                    modifier = Modifier
                        .constrainAs(searchAnimatedIcon) {
                            top.linkTo(parent.top, margin = 12.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            height = Dimension.value(50.dp)
                            width = Dimension.value(50.dp)
                        })




                Text(
                    text = "Searching for nearby assistance...",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .constrainAs(searchingText) {
                            top.linkTo(searchAnimatedIcon.bottom, margin = 10.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        })

                OutlinedButton(
                    onClick = onClickButton,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(width = 1.dp, color = ShowableDisplaysBorderColor),
                    modifier = Modifier
                        .padding(8.dp)
                        .constrainAs(cancelButton) {
                            top.linkTo(searchingText.bottom, margin = 4.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                        }) {
                    Text(
                        text = "CANCEL",
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )


                }
            }
        }
    }
}


@Composable
private fun BottomSheetRescue(displayedText: String) {
    MappingBottomSheet() {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {


            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ShowableDisplaysBackgroundColor)) {

                val (animatedIcon, arrivedText) = createRefs()

                Text(
                    text = displayedText,
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .constrainAs(arrivedText) {
                            top.linkTo(parent.top, margin = 25.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        })


                AnimatedImage(
                    imageId = R.drawable.ic_ellipsis,
                    modifier = Modifier
                        .constrainAs(animatedIcon) {
                            top.linkTo(arrivedText.bottom, margin = 10.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            width = Dimension.value(55.dp)
                            bottom.linkTo(parent.bottom, margin = 15.dp)

                        })

            }

        }

    }
}

@Composable
fun BottomSheetRescuerArrived() {
    BottomSheetRescue(displayedText = "Your rescuer has arrived.")
}

@Composable
fun DestinationReachedBottomSheet() {
    BottomSheetRescue(displayedText = "You have reached your destination.")
}


@Composable
fun BottomSheetRoundedButton(imageId: Int, buttonSubtitle: String, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            modifier = Modifier.size(56.dp),
            onClick = onClick,
            shape = CircleShape,

            colors = ButtonDefaults.buttonColors(
                backgroundColor = RoundedButtonBackgroundColor,
                contentColor = RoundedButtonContentColor)) {

            Icon(
                painter = painterResource(id = imageId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(45.dp))
        }

        Text(
            text = buttonSubtitle,
            color = RoundedButtonSubtitleColor,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center)
    }


}

@Composable
fun OnGoingRescueBottomSheet(estimatedTimeRemaining: String) {
    MappingBottomSheet() {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ShowableDisplaysBackgroundColor)) {

                val (timeRemaining, roundedButtonSection) = createRefs()

                Text(
                    text = "Estimated time of arrival: $estimatedTimeRemaining",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.constrainAs(timeRemaining) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })


                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .constrainAs(roundedButtonSection) {
                            top.linkTo(timeRemaining.bottom, margin = 22.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom, margin = 17.dp)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly) {

                    BottomSheetRoundedButton(
                        imageId = R.drawable.ic_call,
                        buttonSubtitle = "Call") {

                    }


                    BottomSheetRoundedButton(
                        imageId = R.drawable.ic_chat,
                        buttonSubtitle = "Chat") {

                    }


                    BottomSheetRoundedButton(
                        imageId = R.drawable.ic_cancel,
                        buttonSubtitle = "Cancel") {

                    }
                }
            }
        }
    }
}



