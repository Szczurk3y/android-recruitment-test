package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawAlbum(
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable
