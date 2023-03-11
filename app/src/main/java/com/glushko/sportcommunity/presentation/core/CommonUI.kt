package com.glushko.sportcommunity.presentation.core

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.glushko.sportcommunity.R

@Composable
fun Loader(){
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}
@Composable
fun DoSomething(message: String, textButton: String = stringResource(id = R.string.retry), doSomething: ()->Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Button(onClick = doSomething) {
            Text(text = textButton)
        }
    }
}

@Composable
fun EmptyText(textMessage: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = textMessage,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}