package com.glushko.sportcommunity.presentation.match_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment

@Composable
fun ItemMatchSegment(segment: MatchSegment) {
    when(segment) {
        MatchSegment.START -> R.string.match_detail_segment_start
        MatchSegment.END -> R.string.match_detail_segment_end
        MatchSegment.BREAK -> R.string.match_detail_segment_break
        else -> null
    }?.let { textSegment ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_whistle), contentDescription = "")
            Text(text = stringResource(id = textSegment))
        }
    }
}