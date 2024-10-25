package com.alpha.showcase.common.ui.play

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alpha.showcase.common.ui.settings.SHOWCASE_MODE_FRAME_WALL
import kotlinx.coroutines.delay
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt


@Composable
fun FrameWallLayout(
    row: Int,
    column: Int,
    data: List<Any>,
    random: Boolean = false,
    duration: Long = DEFAULT_PERIOD,
    fitSize: Boolean = true
) {
    fun randomGet() = data[nextInt(data.size)]

    val currentShowFrameList = remember(row, column) {

        val list = mutableListOf<Any>()
        if (random) {
            repeat(row * column) {
                list.add(randomGet())
            }
        } else {
            if (data.size > row * column)
                list.addAll(data.subList(0, row * column))
            else
                list.addAll(data)
            if (list.size < row * column) {
                repeat(row * column - list.size) {
                    list.add(randomGet())
                }
            }
        }
        list.toMutableStateList()
    }

    Column {
        repeat(row) { i ->
            Row(modifier = Modifier.weight(1f / row)) {
                repeat(column) { j ->
                    Column(modifier = Modifier.weight(1f / column)) {
                        AnimatedContent(
                            currentShowFrameList[i * column + j],
                            transitionSpec = {
                                if (nextBoolean()) {
                                    if (nextBoolean()) {
                                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                            slideOutHorizontally { width -> -width } + fadeOut())
                                    } else {
                                        (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                            slideOutVertically { height -> -height } + fadeOut())
                                    }

                                } else {
                                    if (nextBoolean()) {
                                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                            slideOutHorizontally { width -> width } + fadeOut())
                                    } else {
                                        (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                                            slideOutVertically { height -> height } + fadeOut())
                                    }

                                }.using(
                                    // Disable clipping since the faded slide-in/out should
                                    // be displayed out of bounds.
                                    SizeTransform(clip = false)
                                )
                            }, label = "slide anim"
                        ) {
                            PagerItem(
                                modifier = Modifier.padding(2.dp),
                                data = it,
                                fitSize,
                                parentType = SHOWCASE_MODE_FRAME_WALL
                            )
                        }
                    }
                }
            }
        }
    }

    val style by remember {
        mutableIntStateOf(1)
    }


    when (style) {
        0 -> {
            AnimateStyle0(
                row,
                column,
                currentShowFrameList,
                animateDuration = if (duration <= 0) DEFAULT_PERIOD else duration
            ) {
                randomGet()
            }
        }

        1 -> {
            AnimateStyle1(
                row,
                column,
                currentShowFrameList,
                animateDuration = if (duration <= 0) DEFAULT_PERIOD else duration
            ) {
                randomGet()
            }
        }

        else -> {
            AnimateStyle1(
                row,
                column,
                currentShowFrameList,
                animateDuration = if (duration <= 0) DEFAULT_PERIOD else duration
            ) {
                randomGet()
            }
        }
    }

}

// replace the old frame with a new frame
@Composable
fun AnimateStyle0(
    row: Int,
    column: Int,
    frameList: SnapshotStateList<Any>,
    animateDuration: Long,
    randomGet: () -> Any
) {

    var preIndex by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit) {
        delay(animateDuration)
        while (true) {

            repeat(row * column / 10 + 1) {
                preIndex = getRandomIntNoRe(frameList.size, preIndex)
                frameList.removeAt(preIndex)
                frameList.add(preIndex, randomGet())
                delay(1000)
            }
            delay(animateDuration)
        }
    }
}

@Composable
fun AnimateStyle1(
    row: Int,
    column: Int,
    frameList: SnapshotStateList<Any>,
    animateDuration: Long,
    randomGet: () -> Any
) {

    var preIndex by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit) {
        delay(animateDuration)
        while (true) {
            preIndex = nextInt(column)
            repeat(row) {
                val index = (column * it + (preIndex + it) % column) % frameList.size
                frameList.removeAt(index)
                frameList.add(index, randomGet())
                delay(300)
            }
            delay(animateDuration)
        }
    }
}

fun getRandomIntNoRe(bound: Int, candi: Int?): Int {
    val nextInt = nextInt(bound)
    return if (candi == null || nextInt != candi) nextInt else getRandomIntNoRe(bound, candi)
}

