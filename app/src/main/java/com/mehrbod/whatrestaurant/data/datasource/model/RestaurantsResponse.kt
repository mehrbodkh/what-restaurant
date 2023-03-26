package com.mehrbod.whatrestaurant.data.datasource.model

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("Restaurants")
    val restaurants: List<Restaurant>
)

data class Restaurant(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("LogoUrl")
    val logoUrl: String,
    @SerializedName("IsOpenNow")
    val isOpenNow: Boolean,
    @SerializedName("CuisineTypes")
    val cuisineTypes: List<CuisineType>,
    @SerializedName("Rating")
    val rating: Rating
)

data class CuisineType(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
)

data class Rating(
    @SerializedName("Count")
    val count: Int,
    @SerializedName("Average")
    val average: Double,
    @SerializedName("StarRating")
    val starRating: Double,
)
