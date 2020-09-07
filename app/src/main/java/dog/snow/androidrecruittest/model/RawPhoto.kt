package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawPhoto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable