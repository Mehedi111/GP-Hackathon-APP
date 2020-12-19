package com.ms.gphackathonproject.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gm.lollipop.storage.db.download.OfflineDaoAccess
import com.gm.lollipop.storage.db.download.OfflineContent
import com.ms.gphackathonproject.utils.converter.MainContentsTypeConverter

/**
 * Created by Mehedi Hasan on 12/10/2020.
 */

@Database(entities = [OfflineContent::class], version = 1, exportSchema = false)
@TypeConverters(MainContentsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun offlineDaoAccess(): OfflineDaoAccess
}