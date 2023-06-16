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
import coil.compose.AsyncImage
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.model.CafeDummy
import com.dicoding.c23ps051.caferecommenderapp.ui.common.isOpen
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.CafeRecommenderAppTheme
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.Gray

@Composable
fun CafeItem(
    id: String,
    fromFavorite: Boolean,
    thumbnail: String,
    name: String,
    rating: Double,
    review: String,
    onClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val nameLetterLimit = 25
    var newName = ""
    if (name.length > nameLetterLimit) {
        for (i in 0..nameLetterLimit) {
            newName += name[i]
        }
        newName += "..."
    } else {
        newName = name
    }

    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .width(140.dp)
            .height(172.dp)
    ) {
        Column (
            modifier = Modifier
                .clickable { onClick(id, fromFavorite) },
        ) {
            AsyncImage(
                model = thumbnail,
                contentDescription = "Image of $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = newName,
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
                        text = "$rating/5.0 ($review)",
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
        val cafe = CafeDummy.cafeList[0]
        CafeItem(
            id = cafe.id,
            fromFavorite = false,
            thumbnail = cafe.thumbnail,
            name = cafe.name,
            rating = cafe.rating,
            review = cafe.review,
            onClick = { _, _ -> }
        )
    }
}

@Composable
fun CafeItemLarge(
    id: String,
    fromFavorite: Boolean,
    thumbnail: String,
    name: String,
    address: String,
    rating: Double,
    review: String,
    priceCategory: String,
    openingHour: Int,
    closingHour: Int,
    modifier: Modifier = Modifier,
    enableFavoriteIcon: Boolean = false,
    onClick: (String, Boolean) -> Unit = { _, _ -> },
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
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick(id, fromFavorite) },
        shape = MaterialTheme.shapes.large,
    ) {
        Box {
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
                AsyncImage(
                    model = thumbnail,
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
                                append("$rating/5.0 ($review)")
                                withStyle(SpanStyle(color = Gray)) {
                                    append(" | ")
                                }
                                append(priceCategory)
                                withStyle(SpanStyle(color = Gray)) {
                                    append(" | ")
                                }
                                append(
                                    if (isOpen(openingHour, closingHour)) {
                                        stringResource(id = R.string.open)
                                    } else {
                                        stringResource(id = R.string.closed)
                                    }
                                )
                            },
                            style = MaterialTheme.typography.bodySmall
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
        val cafe = CafeDummy.cafeList[0]
        CafeItemLarge(
            id = cafe.id,
            fromFavorite = false,
            thumbnail = cafe.thumbnail,
            name = cafe.name,
            address = cafe.address,
            rating = cafe.rating,
            review = cafe.review,
            openingHour = cafe.openingHour,
            closingHour = cafe.closingHour,
            priceCategory = cafe.priceCategory,
        )
    }
}