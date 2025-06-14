package com.example.revealswipelazycolumn

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.example.revealswipelazycolumn.model.ItemsModel
import com.example.revealswipelazycolumn.state.SwipeState


@Composable
fun <T : Any, M : ItemsModel<T>> SwipeLazyColumn(
    modifiercontaner: Modifier,
    shape: androidx.compose.ui.graphics.Shape,
    space : Dp = 5.dp,
    items: List<M>,
    leftActions: @Composable BoxScope.(M, close:() -> Unit) -> Unit,
    rightActions: @Composable BoxScope.(M,close:()-> Unit) -> Unit,
    content: @Composable (M) -> Unit
){
    val listState = rememberLazyListState()
    var currentSwipedItemId by remember { mutableStateOf<T?>(null) }

    LazyColumn(state = listState) {
        items(
            items =items,
            key = { it.id as Any }
        ) { item ->
            val state1 = remember { mutableStateOf(SwipeState.Closed) }
            val isOpen = currentSwipedItemId == item.id
            state1.value = state1.value.takeIf { isOpen } ?: SwipeState.Closed
            RevealSwipe( modifier = modifiercontaner,
                shape = shape,
                swipeStateInit =  state1.value,
                leftActions ={leftActions(item){
                    state1.value = SwipeState.Closed
                    currentSwipedItemId = null
                } } ,
                rightActions = {
                    rightActions(item){
                        state1.value = SwipeState.Closed
                        currentSwipedItemId = null
                    }},
                content ={
                    content(item)},
                funSwiped = {state->
                    if (state != SwipeState.Closed ) {
                        if (currentSwipedItemId != item.id) {
                            currentSwipedItemId = item.id
                        }
                    } else if ( currentSwipedItemId == item.id) {
                        currentSwipedItemId = null
                    }
                    state1.value= state
                })
            Spacer(modifier =Modifier.height(space))
        }
    }
}

