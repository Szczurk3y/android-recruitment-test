package dog.snow.androidrecruittest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class RawUser(
    @PrimaryKey @NonNull val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: RawAddress,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "website") val website: String,
    @ColumnInfo(name = "company") val company: RawCompany
) : Parcelable {
    companion object {
        const val USER_KEY = "USER_KEY"
    }
    @Parcelize
    data class RawAddress(
        @ColumnInfo(name = "street") val street: String,
        @ColumnInfo(name = "suite") val suite: String,
        @ColumnInfo(name = "city") val city: String,
        @ColumnInfo(name = "zipcode") val zipcode: String,
        @ColumnInfo(name = "geo") val geo: RawGeo
    ) : Parcelable {
        @Parcelize
        data class RawGeo(
            @ColumnInfo(name = "lat") val lat: String,
            @ColumnInfo(name = "lng") val lng: String) : Parcelable
    }

    @Parcelize
    data class RawCompany(
        @ColumnInfo(name = "company_name") val name: String,
        @ColumnInfo(name = "catchPhrase") val catchPhrase: String,
        @ColumnInfo(name = "bs") val bs: String
    ) : Parcelable
}