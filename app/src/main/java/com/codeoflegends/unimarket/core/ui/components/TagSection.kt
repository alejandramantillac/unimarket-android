package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun TagSection(
    tags: List<TagType>,
    modifier: Modifier = Modifier,
    onTagClick: ((TagType) -> Unit)? = null
) {
    var showAllTags by remember { mutableStateOf(false) }
    val visibleTags = if (showAllTags) tags else tags.take(3)
    val hasMoreTags = tags.size > 3

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp,
        mainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSize = SizeMode.Expand
    ) {
        visibleTags.forEach { tag ->
            Tag(
                tagType = tag,
                onClick = onTagClick?.let { { it(tag) } }
            )
        }
        
        if (hasMoreTags && !showAllTags) {
            Tag(
                tagType = TagType.MORE,
                onClick = { showAllTags = true }
            )
        }
    }
}
