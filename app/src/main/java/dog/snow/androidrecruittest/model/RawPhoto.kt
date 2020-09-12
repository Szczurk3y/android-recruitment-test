package dog.snow.androidrecruittest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "photo_table")
@Parcelize
data class RawPhoto(
    @PrimaryKey @NonNull val id: Int,
    @ColumnInfo(name = "albumId") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String
): Parcelable {
    companion object {
        const val PHOTO_KEY = "PHOTO_KEY"
    }
}