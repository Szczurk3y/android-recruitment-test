package dog.snow.androidrecruittest.model.room

import androidx.room.TypeConverter
import dog.snow.androidrecruittest.model.RawUser
import java.util.*

class RawUserConverter {

    @TypeConverter
    fun fromRawAddress(rawAddress: RawUser.RawAddress?): String? = if (rawAddress != null) {
        String.format(Locale.US, "%d,%d,%d,%d,%d,%d", rawAddress.street, rawAddress.suite, rawAddress.city, rawAddress.zipcode, rawAddress.geo.lat, rawAddress.geo.lng)
    } else {
        null
    }

//    @TypeConverter
//    fun fromRawGeo(rawGeo: RawUser.RawAddress.RawGeo?): String? = if (rawGeo != null) {
//        String.format(Locale.US, "%d,%d", rawGeo.lat, rawGeo.lng)
//    } else {
//        null
//    }

    @TypeConverter
    fun fromRawCompany(rawCompany: RawUser.RawCompany?): String? = if (rawCompany != null) {
        String.format(Locale.US, "%d,%d,%d", rawCompany.name, rawCompany.catchPhrase, rawCompany.bs)
    } else {
        null
    }

    @TypeConverter
    fun toRawAddress(value: String?): RawUser.RawAddress? = if (value != null) {
        val pieces = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        RawUser.RawAddress(
            street = pieces[0],
            suite = pieces[1],
            city = pieces[2],
            zipcode = pieces[3],
            geo = RawUser.RawAddress.RawGeo(
                pieces[4],
                pieces[5]
            )
        )
    } else {
        null
    }

    @TypeConverter
    fun toRawCompany(value: String?): RawUser.RawCompany? = if (value != null) {
        val pieces = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        RawUser.RawCompany(
            name = pieces[0],
            catchPhrase = pieces[1],
            bs = pieces[2]
        )
    } else {
        null
    }
}