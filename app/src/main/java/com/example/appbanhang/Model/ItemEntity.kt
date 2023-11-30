package com.example.appbanhang.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ItemDB")
class ItemEntity(
    @PrimaryKey(autoGenerate = false) val generateId: String? = null,
    @ColumnInfo(name = "Image") var image: String? = null,
    @ColumnInfo(name = "TenSanPham") var tenSP: String? = null,
    @ColumnInfo(name = "Price") var price: String? = null,
    @ColumnInfo(name = "Describe") var describe: String? = null,
    @ColumnInfo(name = "Type") var type: String? = null,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
): Parcelable