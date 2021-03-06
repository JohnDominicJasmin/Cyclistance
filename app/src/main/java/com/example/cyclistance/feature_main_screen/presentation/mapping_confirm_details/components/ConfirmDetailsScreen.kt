package com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details.components

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cyclistance.feature_main_screen.presentation.common.MappingAdditionalMessage
import com.example.cyclistance.feature_main_screen.presentation.common.MappingButtonNavigation
import com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details.ConfirmDetailsEvent
import com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details.ConfirmDetailsUiEvent
import com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details.ConfirmDetailsViewModel
import com.example.cyclistance.navigation.Screens

import com.example.cyclistance.theme.*
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ConfirmDetailsScreen(
    viewModel: ConfirmDetailsViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
    navigateTo: (destination: String, popUpToDestination: String?) -> Unit) {

    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ConfirmDetailsUiEvent.ShowMappingScreen -> {
                    onPopBackStack()
                }

                is ConfirmDetailsUiEvent.ShowNoInternetScreen -> {
                    navigateTo(Screens.NoInternetScreen.route, null)
                }

                is ConfirmDetailsUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)) {

        val (bikeTypeDropDownList, buttonDescriptionSection, additionalMessageSection, buttonNavButtonSection, noteText) = createRefs()

        DropDownBikeList(modifier = Modifier
            .constrainAs(bikeTypeDropDownList) {
                top.linkTo(parent.top, margin = 25.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                width = Dimension.percent(0.9f)
                height = Dimension.wrapContent
            },
            errorMessage = state.bikeTypeErrorMessage,
            selectedItem = state.bikeType,
            onClickItem = { selectedItem ->
                viewModel.onEvent(event = ConfirmDetailsEvent.SelectBikeType(selectedItem))
            })

        ButtonDescriptionDetails(
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(buttonDescriptionSection) {
                    top.linkTo(bikeTypeDropDownList.bottom, margin = 15.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    height = Dimension.wrapContent
                    width = Dimension.percent(0.9f)
                },
            selectedOption = state.description,
            errorMessage = state.descriptionErrorMessage,
            onClick = { selectedDescription ->
                viewModel.onEvent(event = ConfirmDetailsEvent.SelectDescription(selectedDescription))
            })

        MappingAdditionalMessage(
            modifier = Modifier
                .constrainAs(additionalMessageSection) {
                    top.linkTo(buttonDescriptionSection.bottom, margin = 15.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    height = Dimension.percent(0.25f)
                    width = Dimension.percent(0.9f)

                },
            message = state.message,
            onValueChange = { message ->
                viewModel.onEvent(event = ConfirmDetailsEvent.EnteredMessage(message))
            }
        )

        Text(
            text = "This will send a request to nearby bikers.",
            color = Black440,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(noteText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(buttonNavButtonSection.top)
            }
        )

        MappingButtonNavigation(
            modifier = Modifier
                .constrainAs(buttonNavButtonSection) {
                    top.linkTo(additionalMessageSection.bottom, margin = 50.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    height = Dimension.wrapContent
                    width = Dimension.percent(0.9f)
                },
            onClickCancelButton = {
                onPopBackStack()
            },
            onClickConfirmButton = {
                viewModel.onEvent(event = ConfirmDetailsEvent.Save)
            })


    }


}

@Preview
@Composable
fun ConfirmationDetailsPreview() {
    CyclistanceTheme(false) {
        ConfirmDetailsScreen(onPopBackStack = {}) { _, _ -> }
    }
}





