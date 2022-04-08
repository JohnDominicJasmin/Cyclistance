package com.example.cyclistance.feature_mapping.presentation.mapping_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.cyclistance.feature_mapping.presentation.common.MappingButtonDialog
import com.example.cyclistance.feature_mapping.presentation.mapping_cancellation_screen.components.RadioButtonsSection
import com.example.cyclistance.theme.BackgroundColor



@Preview
@Composable
fun MappingCancellationReasonScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BackgroundColor)) {

        val (radioButtonsSection, additionalMessageSection,buttonNavButtonSection) = createRefs()

    RadioButtonsSection(modifier = Modifier
        .fillMaxSize()
        .constrainAs(radioButtonsSection) {
            top.linkTo(parent.top, margin = 15.dp)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        })

        MappingAdditionalMessage(
            modifier = Modifier
                .constrainAs(additionalMessageSection) {
                    top.linkTo(radioButtonsSection.bottom, margin = 15.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    height = Dimension.percent(0.30f)
                    width = Dimension.percent(0.9f)

                }
        )


        MappingButtonDialog(
            modifier = Modifier
                .constrainAs(buttonNavButtonSection) {
                    top.linkTo(additionalMessageSection.bottom, margin = 50.dp)
                    bottom.linkTo(parent.bottom,margin = 5.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    height = Dimension.percent(0.1f)
                    width = Dimension.percent(0.9f)
                },
            onClickCancelButton = {

            },
            onClickConfirmButton = {

            })



    }
}


