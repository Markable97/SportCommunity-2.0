package com.glushko.sportcommunity.presentation.core

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

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
fun DoSomething(message: String, textButton: String, doSomething: ()->Unit){
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