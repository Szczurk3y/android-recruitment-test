package dog.snow.androidrecruittest.model.room

import androidx.room.TypeConverter
import dog.snow.androidrecruittest.model.RawUser
import java.util.*

class RawUserConverter {



    @TypeConverter
    fun fromRawGeo(rawGeo: RawUser.RawAddress.RawGeo?): String? = if (rawGeo != null) {
        String.format(Locale.US, "%s,%s", rawGeo.lat, rawGeo.lng)
    } else {
        null
    }

    @TypeConverter
    fun toRawGeo(value: String?): RawUser.RawAddress.RawGeo? = if (value != null) {
        val pieces = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        RawUser.RawAddress.RawGeo(
            lat = pieces[0],
            lng = pieces[1]
        )
    } else {
        null
    }


    @TypeConverter
    fun fromRawAddress(rawAddress: RawUser.RawAddress?): String? = if (rawAddress != null) {
        String.format(Locale.US, "%s,%s,%s,%s,%s", rawAddress.street, rawAddress.suite, rawAddress.city, rawAddress.zipcode, rawAddress.geo.lng, rawAddress.geo.lng)
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
            geo = toRawGeo(value)!!
        )
    } else {
        null
    }

    @TypeConverter
    fun fromRawCompany(rawCompany: RawUser.RawCompany?): String? = if (rawCompany != null) {
        String.format(Locale.US, "%s,%s,%s", rawCompany.name, rawCompany.catchPhrase, rawCompany.bs)
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