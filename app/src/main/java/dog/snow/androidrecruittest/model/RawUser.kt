package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawUser(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: RawAddress,
    val phone: String,
    val website: String,
    val company: RawCompany
) : Parcelable {
    @Parcelize
    data class RawAddress(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: RawGeo
    ) : Parcelable {
        @Parcelize
        data class RawGeo(val lat: String, val lng: String) : Parcelable
    }

    @Parcelize
    data class RawCompany(
        val name: String,
        val catchPhrase: String,
        val bs: String
    ) : Parcelable
}