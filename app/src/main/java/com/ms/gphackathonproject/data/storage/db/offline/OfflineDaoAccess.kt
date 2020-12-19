package com.gm.lollipop.storage.db.download

import androidx.room.*
import io.reactivex.Single

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

@Dao
interface OfflineDaoAccess {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createOfflineContent(offlineDownload: OfflineDownload): Single<Long>

    @Query("SELECT * FROM OfflineDownload WHERE id = :contentId")
    fun searchOfflineContent(contentId: Int): Single<OfflineDownload>

    @Query("SELECT * FROM OfflineDownload WHERE isWishList = 1")
    fun getAllWishListContent(): Single<List<OfflineDownload>>


    @Query("UPDATE OfflineDownload SET isWishList = 1 WHERE id = :contentId")
    fun addWishListFlag(contentId: Int): Single<Int>

    @Query("UPDATE OfflineDownload SET isWishList = 0 WHERE id = :contentId")
    fun removeWishListFlag(contentId: Int): Single<Int>

    @Query("UPDATE OfflineDownload SET isWishList = 0")
    fun removeAllWishListFlag(): Single<Int>



}