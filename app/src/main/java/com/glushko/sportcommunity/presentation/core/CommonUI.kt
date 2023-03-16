package com.glushko.sportcommunity.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glushko.sportcommunity.R

@Composable
fun Loader() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}

@Composable
fun DoSomething(
    message: String,
    textButton: String = stringResource(id = R.string.retry),
    doSomething: () -> Unit
) {
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
    ) {
        Text(
            text = textMessage,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

//Region widget
@Composable
fun Widget(
    title: String,
    navigationAction: (() -> Unit)? = null,
    filling: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(enabled = navigationAction != null) {
                navigationAction?.invoke()
            },
    ) {
        Column(modifier = Modifier.padding(5.dp))/*contentPadding)*/ {
            TitleNavigate(
                textTitle = title,
                isVisibleIconNavigate = navigationAction != null,
                navigationAction = navigationAction
            )
            filling.invoke()
        }
    }
}
//end region widget

//Region title
class TittleNavigatePreview(
    override val values: Sequence<String> = listOf("Заголовок").asSequence()
) : PreviewParameterProvider<String>

@Preview(
    showBackground = true
)
@Composable
fun TitleNavigate(
    isVisibleIconNavigate: Boolean = true,
    navigationAction: (() -> Unit)? = null,
    @PreviewParameter(TittleNavigatePreview::class)
    textTitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = navigationAction != null) {
                navigationAction?.invoke()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textTitle,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        if (isVisibleIconNavigate) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
// end title region