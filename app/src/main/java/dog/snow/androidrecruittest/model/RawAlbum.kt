package dog.snow.androidrecruittest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "album_table")
data class RawAlbum(
    @PrimaryKey @NonNull val id: Int,
    val userId: Int,
    val title: String
)
