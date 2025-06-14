package com.example.revealswipelazycolumn
import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import com.example.revealswipelazycolumn.state.SwipeState
import kotlin.math.roundToInt
@Composable
fun   RevealSwipe(
    modifier: Modifier,
    shape: Shape,
    leftActions: @Composable BoxScope.() -> Unit,
    rightActions: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit,
    swipeStateInit: SwipeState,
    funSwiped : (SwipeState)-> Unit
)
{
    var leftWidth by remember { mutableFloatStateOf(0f) }
    var rightWidth by remember { mutableFloatStateOf(0f) }
    var swipeState by remember { mutableStateOf(swipeStateInit) }
    if (swipeState != SwipeState.Moving && swipeState != swipeStateInit) {
        swipeState = swipeStateInit
    }
    var offsetX by remember { mutableFloatStateOf(0f) }

    val animatedOffsetX by animateFloatAsState(
        targetValue = when (swipeState) {
            SwipeState.Moving ->offsetX
            SwipeState.Closed -> 0f
            SwipeState.LeftRevealed -> leftWidth
            SwipeState.RightRevealed -> -rightWidth

        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow)
    )
    Surface (
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = shape
    ) {
        Box{
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .onSizeChanged { leftWidth = it.width.toFloat() }
            ) {
                leftActions()
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .onSizeChanged { rightWidth = it.width.toFloat() }
            ) {
                rightActions()
            }
            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                    .fillMaxWidth()
                    .pointerInput(leftWidth, rightWidth) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                var newOffset = 0f
                                newOffset = if (dragAmount > 0) {
                                    (offsetX + dragAmount).coerceIn(0f, leftWidth)

                                } else {
                                    (offsetX + dragAmount).coerceIn(-rightWidth, 0f)

                                }
                                offsetX = newOffset
                                if(swipeState != SwipeState.Moving){
                                    swipeState = SwipeState.Moving
                                    funSwiped(swipeState)

                                }

                                change.consume()
                            },
                            onDragEnd = {
                                swipeState = when {
                                    offsetX > leftWidth / 2 -> SwipeState.LeftRevealed
                                    offsetX < -rightWidth / 2 -> SwipeState.RightRevealed
                                    else -> SwipeState.Closed
                                }
                                funSwiped(swipeState)

                            },
                            onDragCancel = {
                                swipeState = SwipeState.Closed
                            }
                        )
                    }
            ) {
                content()
            }
        }


    }
}