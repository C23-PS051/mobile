package com.dicoding.c23ps051.caferecommenderapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun CafeItem(
    id: Long,
    thumbnail: String,
    name: String,
    rating: Double,
    ratingCount: Int,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .width(140.dp)
    ) {
        Column (
            modifier = Modifier
                .clickable { onClick(id) },
        ) {
            Image(
                painter = painterResource(id = R.drawable.cafe),
                contentDescription = "Image of a Cafe",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$rating/5 ($ratingCount)",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CafeItemPreview() {
    CafeRecommenderAppTheme {
        CafeItem(
            id = 0,
            thumbnail = "",
            name = "Cafe",
            rating = 4.0,
            ratingCount = 100,
            onClick = {}
        )
    }
}

@Composable
fun CafeItemLarge(
    id: Long,
    thumbnail: String,
    name: String,
    address: String,
    rating: Double,
    ratingCount: Int,
    distance: Double,
    condition: Boolean,
    modifier: Modifier = Modifier,
    enableFavoriteIcon: Boolean = false,
    onClick: (Long) -> Unit = {},
) {
    val addressLetterLimit = 50
    var newAddress = ""
    if (address.length > addressLetterLimit) {
        for (i in 0..addressLetterLimit) {
            newAddress += address[i]
        }
        newAddress += "..."
    } else {
        newAddress = address
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
//            .border(
//                width = 1.dp,
//                color = Gray,
//                shape = MaterialTheme.shapes.large
//            )
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick(id) },
        shape = MaterialTheme.shapes.large,
    ) {
        Box {
//            IconButton(
//                onClick = { /*TODO*/ },
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.FavoriteBorder,
//                    contentDescription = stringResource(id = R.string.add_to_favorite),
//                    modifier = Modifier.size(20.dp)
//                )
//            }
            if (enableFavoriteIcon) {
                FavoriteButton(
                    onClick = { /*TODO*/ },
                    isFavorite = true, /*TODO: CHANGE THIS, STILL DUMMY*/
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(40.dp),
                    size = 16,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cafe),
                    contentDescription = "Image of $name",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(104.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp, end = 28.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = newAddress,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painterResource(id = R.drawable.star),
                            contentDescription = stringResource(id = R.string.rating_count)
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("$rating/5 ($ratingCount)")
                                withStyle(SpanStyle(color = Gray)) {
                                    append(" | ")
                                }
                                append("$distance km")
                                withStyle(SpanStyle(color = Gray)) {
                                    append(" | ")
                                }
                                append(
                                    if (condition) {
                                        stringResource(id = R.string.open)
                                    } else {
                                        stringResource(id = R.string.closed)
                                    }
                                )
                            },
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CafeItemLargePreview() {
    CafeRecommenderAppTheme {
        CafeItemLarge(
            id = 1,
            thumbnail = "",
            name = "Cafe",
            address = "Jl. Raya Ragunan No. 57, Kec. Pasar Minggu, RT.5/RW.4, Kota Jakarta Selatan",
            rating = 4.7,
            ratingCount = 10,
            distance = 2.0,
            condition = true,
        )
    }
}