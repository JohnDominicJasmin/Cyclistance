package com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun DontHaveAccountText() {
    Box(modifier = Modifier.wrapContentSize().layoutId(ConstraintsItem.DontHaveAccountText.layoutId)){
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)){
                append(text = "Don't have an account?" )
            }
            withStyle(style = SpanStyle(color = Color(0xFF799DFC),fontWeight = FontWeight.SemiBold,fontSize = 14.sp)){
                append(" ")
                append(text = "Sign up")
            }
        })
    }
}

@Preview
@Composable
fun PreviewDonthaveAccountText() {
    DontHaveAccountText()
}