@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehrbod.whatrestaurant.presentation

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.mehrbod.whatrestaurant.R
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant

@Composable
fun RestaurantsScreen(
    viewModel: RestaurantsViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var text by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier
            .padding(16.dp)
            .imePadding()
    ) { _ ->
        Column {
            SearchBar(text) { text = it }
            when (state) {
                is UiState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is UiState.Error -> {
                    Text(text = (state as UiState.Error).message, modifier = Modifier.fillMaxSize())
                }
                is UiState.Loaded -> {
                    RestaurantsList(restaurants = (state as UiState.Loaded).restaurants)
                }
            }
        }
    }
}

@Composable
fun SearchBar(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {}, modifier = Modifier.padding(4.dp)) {
                Icon(painterResource(id = R.drawable.baseline_gps_fixed_24), "")
            }
        },
        placeholder = {
            Text(text = "Enter your postcode")
        }
    )
}

@Composable
fun RestaurantsList(restaurants: List<Restaurant>) {
    LazyColumn(content = {
        items(restaurants, key = { it.id }) {
            RestaurantCard(it)
        }
    })
}

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            GifImage(url = restaurant.logoUrl, modifier = Modifier.size(48.dp))
            Text(restaurant.name)
        }
    }

}

@Composable
fun GifImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(url, imageLoader = imageLoader),
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )
}

