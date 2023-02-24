package com.glushko.sportcommunity.presentation.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.presentation.base.BaseFragment
import com.glushko.sportcommunity.presentation.core.DoSomething
import com.glushko.sportcommunity.presentation.core.Loader
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment: BaseFragment() {

    private val viewModel: GalleryViewModel by viewModels()

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
        val imagesResponse: Result<List<ImageUI>> by viewModel.liveDataGallery.observeAsState(Result.Loading)
        when(imagesResponse){
            is Result.Error -> {
                DoSomething(
                    message = (imagesResponse as Result.Error).exception.message?:"",
                    textButton = getString(R.string.retry)
                ) {
                    viewModel.getMatchMedia()
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

}