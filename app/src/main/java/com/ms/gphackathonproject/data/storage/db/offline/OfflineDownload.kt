package com.gm.lollipop.storage.db.download

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.utils.converter.MainContentsTypeConverter
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mehedi Hasan on 12/10/2020.
 */

@Entity(indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class OfflineDownload(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var d_id: Long? = null,

    var id: Int? = null,

    var isWishList: Int = Constants.NOT_WISHLIST,

    @TypeConverters(MainContentsTypeConverter::class)
    var content: Content? = null

) : Parcelable