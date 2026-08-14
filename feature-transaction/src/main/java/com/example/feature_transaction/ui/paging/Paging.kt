package com.example.feature_transaction.ui.paging

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun Paging(
    enabled: Boolean = true,
    listState: LazyListState,
    preFetchOffset: Int = 0,
    onFetch: () -> Unit
) {
    if (!enabled) return

    val lastVisibleItemIndex by remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
    }

    LaunchedEffect(enabled, lastVisibleItemIndex) {
        val lastVisibleItemIndex = lastVisibleItemIndex
        if (lastVisibleItemIndex == null || lastVisibleItemIndex == 0) return@LaunchedEffect

        if ((lastVisibleItemIndex) >= listState.layoutInfo.totalItemsCount - 1 - preFetchOffset) {
            onFetch()
        }
    }
}
