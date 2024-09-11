package com.alpha.showcase.common.ui.play

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.alpha.showcase.common.ui.view.DataNotFoundAnim
import com.alpha.showcase.common.ui.view.LoadingIndicator

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PagerItem(
  modifier: Modifier = Modifier,
  data: Any,
  fitSize: Boolean = false,
  parentType: Int = -1,
  onComplete: (Any) -> Unit = {}
) {
  val scale = if (fitSize) ContentScale.Fit else ContentScale.Crop

  if (data.isImage()) {
//    val painter = rememberAsyncImagePainter(
//      model = ImageRequest.Builder(LocalPlatformContext.current)
//        .crossfade(300)
//        .data(
//          when (data) {
//            is DataWithType -> data.data
//            is UrlWithAuth -> data.url
//            else -> data
//          }
//        )
//        .apply {
//          if (data is UrlWithAuth) {
//            httpHeaders(NetworkHeaders.Builder().add(data.key, data.value).build())
//          }
//        }
//        .build(),
//      onSuccess = { onComplete(data) },
//      onError = { onComplete(data) }
//    )

    var currentScale by remember { mutableStateOf(scale) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
      AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
          .data(
            when (data) {
              is DataWithType -> data.data
              is UrlWithAuth -> data.url
              else -> data
            }
          )
          .crossfade(600)
          .apply {
            if (data is UrlWithAuth) {
              httpHeaders(NetworkHeaders.Builder().add(data.key, data.value).build())
            }
          }
          .build(),
        contentDescription = null,
        onSuccess = {
          loading = false
          onComplete(data)
        },
        onError = {
          loading = false
          error = true
          onComplete(data)
        },
        onLoading = { loading = true },
        contentScale = currentScale,
        modifier = Modifier
          .fillMaxSize()
          .clickable {
            currentScale = if (currentScale == ContentScale.Crop) {
              ContentScale.Fit
            } else {
              ContentScale.Crop
            }
          },
      )

      AnimatedVisibility(visible = loading, enter = fadeIn(), exit = fadeOut()) {
        LoadingIndicator()
      }

      AnimatedVisibility(visible = error, enter = fadeIn(), exit = fadeOut()) {
        DataNotFoundAnim("")
      }
    }



//    Box(modifier = modifier) {
//      Image(
//        painter = painter,
//        contentDescription = null,
//        modifier = Modifier
//          .fillMaxSize()
//          .clickable {
//            currentScale = if (currentScale == ContentScale.Crop) {
//              ContentScale.Fit
//            } else {
//              ContentScale.Crop
//            }
//          },
//        contentScale = currentScale
//      )
//      when (val state = painter.state) {
//        is AsyncImagePainter.State.Success -> { /* Do nothing */ }
//        is AsyncImagePainter.State.Loading -> {
//          LoadingIndicator()
//        }
//        is AsyncImagePainter.State.Error -> {
//          state.result.throwable.printStackTrace()
//          DataNotFoundAnim("Error")
//        }
//        is AsyncImagePainter.State.Empty -> {
//          DataNotFoundAnim("Empty")
//        }
//      }
//    }
  }
}