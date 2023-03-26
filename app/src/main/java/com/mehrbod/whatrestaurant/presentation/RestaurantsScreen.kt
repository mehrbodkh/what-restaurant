@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehrbod.whatrestaurant.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mehrbod.whatrestaurant.R
import com.mehrbod.whatrestaurant.data.datasource.model.Restaurant

@Composable
fun RestaurantsScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier
            .padding(16.dp)
            .imePadding()
    ) { _ ->
        Column {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            SearchBar(text) { text = it }
            state.errorMessage?.let {
                Text(text = it, modifier = Modifier.fillMaxSize())
            }
            state.restaurants?.let {
                RestaurantsList(restaurants = it)
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
    Text(text = restaurant.name)

}
