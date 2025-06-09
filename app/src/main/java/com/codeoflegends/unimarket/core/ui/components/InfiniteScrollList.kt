package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> InfiniteScrollList(
    modifier: Modifier = Modifier,
    headerContent: @Composable (() -> Unit)? = null,
    emptyContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit,
    items: List<T>,
    isLoading: Boolean,
    titleContent: @Composable (() -> Unit)? = null,
    onLoadMore: () -> Unit,
    orientation: Orientation = Orientation.Vertical
) {
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount

            lastVisibleItemIndex >= totalItems - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoading) {
            onLoadMore()
        }
    }

    HandleOrientation(
        orientation = orientation,
        state = listState,
        modifier = modifier
    ) {
        headerContent?.let {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    it()
                }
            }
        }

        titleContent?.let {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 20.dp),
                ) {
                    it()
                }
            }
        }

        // No hay contenido
        if (items.isEmpty() && !isLoading) {
            if (emptyContent != null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        emptyContent()
                    }
                }
            }
        // Contenido vacío y cargando
        } else if (items.isEmpty() && isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            itemsIndexed(items) { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = if(orientation == Orientation.Vertical) 16.dp else 4.dp,
                            vertical = if (index > 0 && orientation == Orientation.Vertical) 12.dp else 0.dp
                        )
                ) {
                    itemContent(item)
                }
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun HandleOrientation(
    orientation: Orientation,
    state: LazyListState,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    if (orientation == Orientation.Horizontal) {
        LazyRow(
            state = state,
            modifier = modifier.fillMaxWidth(),
        ) {
            content()
        }
    }

    if (orientation == Orientation.Vertical) {
        LazyColumn(
            state = state,
            modifier = modifier.fillMaxHeight(),
        ) {
            content()
        }
    }
}
