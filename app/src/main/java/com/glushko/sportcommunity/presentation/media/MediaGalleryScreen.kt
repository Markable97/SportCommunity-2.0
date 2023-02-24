package com.glushko.sportcommunity.presentation.media

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.presentation.core.ZoomableBox

@Composable
fun GalleryScreen(
    imagesList: List<ImageUI>,
    onClickShare: (Bitmap) -> Unit,
    onClickDownload: (String) -> Unit,
) {
    // store the dialog open or close state
    val openFullImage = remember { mutableStateOf(false to "") }
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
                CardImage(
                    image = image,
                    openFullPhoto = { urlImage ->
                        openFullImage.value = true to urlImage
                    }
                )
            }
        }
    }
    FullPhoto(openFullImage, onClickShare, onClickDownload)
}

@Composable
private fun CardImage(
    image: ImageUI,
    openFullPhoto: (String)-> Unit
){
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.clickable {
            openFullPhoto.invoke(image.imageOrigin)
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullPhoto(
    openFullImage: MutableState<Pair<Boolean, String>>,
    onClickShare: (Bitmap) -> Unit,
    onClickDownload: (String) -> Unit,
) {
    if (openFullImage.value.first){
        var bitmapShare: Bitmap? = null
        Dialog(
            onDismissRequest = { openFullImage.value = false to ""},
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental

            ),
        ) {
            val backgroundColor = Color.Black.copy(alpha = 0.5f)
            Scaffold(
                backgroundColor = backgroundColor,
                topBar = {
                    TopAppBar(
                        backgroundColor = backgroundColor,
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { openFullImage.value = false to "" }) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            Spacer(Modifier.weight(1f, true))
                            IconButton(onClick = {
                                onClickDownload.invoke(openFullImage.value.second)
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_download),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = {
                                onClickShare.invoke(bitmapShare ?: return@IconButton)
                            }) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    ZoomableBox(
                        Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            AsyncImage(
                                model = openFullImage.value.second,
                                placeholder = painterResource(R.drawable.ic_healing_black_36dp),
                                error = painterResource(R.drawable.ic_healing_black_36dp),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        translationX = offsetX,
                                        translationY = offsetY
                                    ),
                                onSuccess = { success ->
                                    bitmapShare = (success.result.drawable as BitmapDrawable).bitmap
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}