package com.glushko.sportcommunity.presentation.media

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.toastLong
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File


@AndroidEntryPoint
class GalleryFragment: BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenGallery()
                }
            }
        }
    }

    @Composable
    private fun ScreenGallery() {
        val imagesResponse: Result<List<ImageUI>> by mainViewModel.liveDataGallery.observeAsState(Result.Loading)
        when(imagesResponse){
            is Result.Error -> {
                DoSomething(
                    message = (imagesResponse as Result.Error).exception.message?:"",
                    textButton = getString(R.string.retry)
                ) {
                    mainViewModel.getMatchMedia()
                }
            }
            Result.Loading -> Loader()
            is Result.Success -> {
                GalleryScreen(
                    imagesList = (imagesResponse as Result.Success<List<ImageUI>>).data,
                    onClickDownload = { url ->
                        (requireActivity() as? MainActivity)?.downloadImage(url)
                    },
                    onClickShare = { bitmap ->
                        mainViewModel.getUriToShare(bitmap, requireActivity().cacheDir.toString(), requireContext())
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroyView()
        mainViewModel.cancelJobMediaMatch()
    }

}