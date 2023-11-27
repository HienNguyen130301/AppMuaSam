package com.example.appbanhang.Data

import android.os.Parcel
import android.os.Parcelable
import com.example.appbanhang.DataBase.ItemEntity

data class DataRecommended(
    var imageUrl: String? = null,
    var tenSP: String? = "",
    var price: String? = "",
    var des: String? = "",
    var type: String? = "",
    var userName: String? =null,
    var userID: String? = null,
    var isFavorite: Boolean = false,
    var key: String? = null){
}
