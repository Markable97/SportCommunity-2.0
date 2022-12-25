package com.glushko.sportcommunity.presentation.media

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.MediaUI

@Composable
fun CreateMediaAlbumScreen(
    mediaList: List<MediaUI>,
    onClickItem: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(
            horizontal = 5.dp
        )
    ){
        mediaList.forEach { media ->
            item {
                CardMedia(media = media, onClickItem = onClickItem)
            }
        }
    }
}

@Composable
fun CardMedia(
    media: MediaUI,
    onClickItem: (Long) -> Unit
){
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.clickable {
            onClickItem.invoke(media.matchId)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = media.preview,
                placeholder = painterResource(R.drawable.ic_healing_black_36dp),
                error = painterResource(R.drawable.ic_healing_black_36dp),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize()
            )
            Text(
                text = media.title,
                modifier = Modifier.padding(start = 5.dp)

            )
            Text(
                text = media.subTitle,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}