package io.indrian16.appcent.data.model

import androidx.room.Entity
import androidx.room.Index
import android.os.Parcelable
import io.indrian16.appcent.util.AppConstant
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = AppConstant.TABLE_ARTICLE,
    primaryKeys = ["slug"],
    indices = [(Index("slug"))])
@Parcelize
data class Article(
    val saveTime: Date?,
    val slug: String,
    val name: String?,
    val description:String?,
    val released: String?,
    val background_image: String?,
    val rating: Double?
): Parcelable