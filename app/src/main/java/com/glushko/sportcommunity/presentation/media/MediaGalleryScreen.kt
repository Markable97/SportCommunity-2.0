package com.glushko.sportcommunity.presentation.media

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.ImageUI

@Composable
fun GalleryScreen(
    imagesList: List<ImageUI>,
    onClickItem: (String) -> Unit
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
        imagesList.forEach { image ->
            item {
                CardImage(image = image, onClickItem = onClickItem)
            }
        }
    }
}

@Composable
private fun CardImage(
    image: ImageUI,
    onClickItem: (String) -> Unit
){
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.clickable {
            onClickItem.invoke(image.imageOrigin)
        }
    ) {
        AsyncImage(
            model = image.imagePreview,
            placeholder = painterResource(R.drawable.ic_healing_black_36dp),
            error = painterResource(R.drawable.ic_healing_black_36dp),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize()
        )

    }
}