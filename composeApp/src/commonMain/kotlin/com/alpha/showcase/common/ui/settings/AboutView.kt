package com.alpha.showcase.common.ui.settings

import Platform
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpha.showcase.common.gitHash
import com.alpha.showcase.common.ui.view.IconItem
import com.alpha.showcase.common.ui.view.TextTitleMedium
import com.alpha.showcase.common.versionCode
import com.alpha.showcase.common.versionName
import isWeb
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import showcaseapp.composeapp.generated.resources.Res
import showcaseapp.composeapp.generated.resources.feedback
import showcaseapp.composeapp.generated.resources.ic_telegram_app
import showcaseapp.composeapp.generated.resources.open_source_license


/**
 *  - About
 *      - Readme
 *      - License
 *      - ChangeLog
 *      - Telegram
 *      - Thanks (Resources and Open Source Libraries)
 *      - Rate
 *      - Feedback
 *      - Donate
 *      - Version
 */


private const val app_page = "https://mrjoechen.github.io/ShowcaseApp/index"

private const val resUrl = "https://github.com/mrjoechen/ShowcaseApp/blob/main/README.md"
private const val telegramChannelUrl = "https://t.me/showcase_app_release"
private const val privacyPolicyUrl = "https://mrjoechen.github.io/ShowcaseApp/privacypolicy"

private const val TAG = "AboutPage"

const val GPL_V3 = "GNU General Public License v3.0"
const val GPL_V2 = "GNU General Public License v2.0"
const val APACHE_V2 = "Apache License 2.0"
const val MIT = "MIT License"
const val UNLICENSE = "The Unlicense"
const val BSD = "BSD 3-Clause License"

@Composable
fun AboutView() {

    var showOpenSourceDialog by remember {
        mutableStateOf(false)
    }

    Column {
        val uriHandler = LocalUriHandler.current
        fun openUrl(url: String) {
            try{
                uriHandler.openUri(url)
            }catch (ex: Exception){
                ex.printStackTrace()
                // todo gen qrcode and show
            }
        }
        TextTitleMedium(text = "About")


        IconItem(
            Icons.Outlined.Info,
            desc = "Info",
            onClick = {
                openUrl(resUrl)
            }){

            Text(
                text = "${versionName}.${gitHash}(${versionCode})",
                color = LocalContentColor.current.copy(0.6f)
            )
        }

        IconItem(
            Res.drawable.ic_telegram_app,
            desc = "Telegram",
            onClick = {
                openUrl(telegramChannelUrl)
            })

        IconItem(
            Icons.Outlined.Feedback,
            desc = stringResource(Res.string.feedback),
            onClick = {
                openUrl(telegramChannelUrl)
            })

        IconItem(
            Icons.Outlined.TipsAndUpdates,
            desc = "Thanks",
            onClick = {
                showOpenSourceDialog = !showOpenSourceDialog
            })

        IconItem(
            Icons.Outlined.PrivacyTip,
            desc = "Privacy Policy",
            onClick = {
                openUrl(privacyPolicyUrl)
            })

//        SwitchItem(
//            Icons.Outlined.Autorenew,
//            false,
//            stringResource(R.string.auto_update)
//        ) {
//
//        }
//

        IconItem(
            Icons.Outlined.ThumbUp,
            desc = "Rate us",
            onClick = {


            }
        )

        if (!isWeb()){
            IconItem(
                Icons.Outlined.ArrowCircleUp,
                desc = "update",
                onClick = {
//                coroutineScope.launch {
//                    UpdateExt.checkForUpdate {
//                        UpdateExt.downloadApk(App.instance, it.apkUrl, it.version)
//                            .collect { downloadStatus ->
//                                if (downloadStatus is DownloadStatus.Finished) {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        App.instance.installLatestApk()
//                                    }
//                                }
//                            }
//                    }
//                }


                }
            )
        }

        IconItem(
            Icons.Outlined.IosShare,
            desc = "Share",
            onClick = {

            }
        )

//        IconItem(
//            icon = Icons.Outlined.NewReleases,
//            desc = stringResource(id = R.string.membership),
//            onClick = {
//                openBottomBilling = !openBottomBilling
//            })
//
//        MemberBillingList(openBottomBilling){
//            openBottomBilling = false
//        }


    }

    if (showOpenSourceDialog) {
        OpenSourceListDialog {
            showOpenSourceDialog = false
        }
    }

}


@Preview
@Composable
fun OpenSourceListDialog(onDismiss: () -> Unit = {}) {
    val uriHandler = LocalUriHandler.current
    fun openUrl(url: String) {
        uriHandler.openUri(url)
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(Res.string.open_source_license)) },
        text = {
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray.copy(0.4f))
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(Modifier.sizeIn(maxHeight = 300.dp, maxWidth = 500.dp)) {
                itemsIndexed(openSourceLibraries) { index, library ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10))
                            .clickable {
                                openUrl(library.second)
                            }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = library.first,
                            modifier = Modifier.padding(vertical = 3.dp),
                            fontSize = 15.sp
                        )
                        Text(text = library.second, fontSize = 12.sp)
                        Text(
                            text = library.third,
                            modifier = Modifier.padding(vertical = 2.dp),
                            fontSize = 10.sp
                        )

                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "OK")
            }
        }
    )
}


val openSourceLibraries = listOf(
    Triple("Accompanist", "https://github.com/google/accompanist", APACHE_V2),
    Triple("AndroidX", "https://developer.android.com/jetpack/androidx", APACHE_V2),
    Triple("AndroidX Work Manager", "https://developer.android.com/topic/libraries/architecture/workmanager", APACHE_V2),
    Triple("Compose", "https://developer.android.com/jetpack/compose", APACHE_V2),
    Triple("Coil", "https://github.com/coil-kt/coil", APACHE_V2),
    Triple("Google Android Material", "https://github.com/material-components/material-components-android", APACHE_V2),
    Triple("Hilt", "https://github.com/google/dagger/tree/main/java/dagger/hilt/android", APACHE_V2),
    Triple("Icons8", "https://icons8.com/", "Universal Multimedia Licensing Agreement for Icons8"),
    Triple("Kotlinx Coroutines", "https://github.com/Kotlin/kotlinx.coroutines", APACHE_V2),
    Triple("Kotlinx Serialization JSON", "https://github.com/Kotlin/kotlinx.serialization", APACHE_V2),
    Triple("Landscape", "https://github.com/skydoves/landscape", APACHE_V2),
    Triple("LeakCanary", "https://square.github.io/leakcanary", APACHE_V2),
    Triple("Lottie", "https://github.com/airbnb/lottie-android", APACHE_V2),
    Triple("Microsoft App Center", "https://github.com/microsoft/appcenter-sdk-android", MIT),
    Triple("OkHttp", "https://square.github.io/okhttp", APACHE_V2),
    Triple("Okio", "https://square.github.io/okio", APACHE_V2),
    Triple("Once", "https://github.com/jonfinerty/Once", APACHE_V2),
    Triple("ProtoBuf", "https://github.com/protocolbuffers/protobuf", APACHE_V2),
    Triple("Retrofit", "https://square.github.io/retrofit/", APACHE_V2),
    Triple("Timber", "https://github.com/JakeWharton/timber", APACHE_V2),
    Triple("Toasty", "https://github.com/GrenderG/Toasty", APACHE_V2),
)