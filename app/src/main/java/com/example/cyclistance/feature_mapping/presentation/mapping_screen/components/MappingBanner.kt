package com.example.cyclistance.feature_mapping.presentation.mapping_screen.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.cyclistance.theme.ShowableDisplaysBackgroundColor
import com.example.cyclistance.theme.Shapes
import com.example.cyclistance.theme.ThemeColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.cyclistance.R
import com.example.cyclistance.theme.DisabledColor
import com.example.cyclistance.feature_mapping.domain.model.MappingBannerModel


@ExperimentalAnimationApi
@Composable
fun MappingDefaultBanner(isVisible: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = isVisible.value,
        enter = expandVertically(),
        exit = shrinkVertically()) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ShowableDisplaysBackgroundColor)) {

            Text(
                modifier = Modifier.padding(
                    top = 25.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp),
                text = "John Doe cancelled the rescue request. The reason is “Problem already fixed.",
                color = Color.White, style = MaterialTheme.typography.body2)

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.End)) {

                TextButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = { isVisible.value = false }) {

                    Text(
                        text = "DISMISS",
                        color = ThemeColor,
                        style = MaterialTheme.typography.subtitle2)
                }

                TextButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = { isVisible.value = false }) {
                    Text(
                        text = "MESSAGE",
                        color = ThemeColor,
                        style = MaterialTheme.typography.subtitle2)
                }
            }
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MappingExpandableBanner( bannerModel: MutableState<MappingBannerModel>) {


    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    with(bannerModel.value) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 180,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = Shapes.medium,
            onClick = { expandedState = !expandedState }, backgroundColor = ShowableDisplaysBackgroundColor) {

            Column(modifier = Modifier.fillMaxWidth()) {

                IconButton(
                    onClick = { expandedState = !expandedState },
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState)
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        tint = Color.White,
                        contentDescription = "Drop down icon",
                        modifier = Modifier.size(30.dp))

                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)) {

                    val (roundedImage, nameText, issueText, distanceAndTime, expandedSection) = createRefs()



                    Image(
                        painter = painterResource(userProfileImage),
                        contentDescription = "User Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .constrainAs(roundedImage) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Text(
                        text =  name,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.constrainAs(nameText) {
                            start.linkTo(roundedImage.end, margin = 15.dp)
                            top.linkTo(parent.top, margin = 13.dp)

                        }
                    )


                    Text(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = DisabledColor)) {
                                append("Issue:")
                            }

                            withStyle(style = SpanStyle(color = Color.White)) {
                                append(" ")
                                append(issue)
                            }

                        }, modifier = Modifier.constrainAs(issueText) {
                            top.linkTo(nameText.bottom)
                            start.linkTo(roundedImage.end, margin = 15.dp)
                        })


                    Text(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append(distanceRemaining)
                            }

                            withStyle(style = SpanStyle(color = DisabledColor)) {
                                append("\n" + timeRemaining)
                            }

                        }, modifier = Modifier.constrainAs(distanceAndTime) {
                            top.linkTo(parent.top, margin = 13.dp)
                            end.linkTo(parent.end, margin = 5.dp)
                        })


                    if (expandedState) {

                        Column(modifier = Modifier.constrainAs(expandedSection) {
                            top.linkTo(issueText.bottom, margin = 10.dp)
                            width = Dimension.matchParent
                        }) {

                            Divider(
                                color = DisabledColor,
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    alignment = Alignment.Start)) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_location),
                                    contentDescription = "Location Icon",
                                    modifier = Modifier.wrapContentSize(), tint = Color.Unspecified)

                                Text(
                                    text = address,
                                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                )
                            }

                            Divider(
                                color = DisabledColor,
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))


                            Text(
                                text = "Message",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Normal,
                                color = DisabledColor
                            )
                            Text(
                                text = message,
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )


                        }


                    }

                }
            }
        }
    }
}
