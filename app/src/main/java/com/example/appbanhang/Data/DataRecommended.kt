package com.example.appbanhang.Data

data class DataRecommended(
    var imageUrl: String? = null,
    var tenSP: String? = "",
    var price: String? = "",
    var des: String? = "",
    var type: String? = "",
    var isAvailable: Boolean? = true,
    var userID: String? = null,
    var userName: String? = null,
    var isFavorite: Boolean = false,
    var key: String? = null){
}
