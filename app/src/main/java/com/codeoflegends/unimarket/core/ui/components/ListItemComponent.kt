package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListItemComponent(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    subtitle: String,
    rightInfo: String? = null,
    tag1: String? = null,
    tag1Color: Color = Color.Gray,
    tag2: String? = null,
    tag2Color: Color = Color.Magenta,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = subtitle, color = Color.Gray, fontSize = 12.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            rightInfo?.let {
                Text(text = it, fontSize = 12.sp, color = Color.Gray)
            }

            Row {
                tag1?.let {
                    Box(
                        modifier = Modifier
                            .background(tag1Color, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = it, fontSize = 10.sp, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                tag2?.let {
                    Box(
                        modifier = Modifier
                            .background(tag2Color, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = it, fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        }
    }
}