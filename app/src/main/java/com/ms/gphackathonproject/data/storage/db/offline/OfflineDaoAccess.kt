package com.gm.lollipop.storage.db.download

import androidx.room.*
import io.reactivex.Single

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

@Dao
interface OfflineDaoAccess {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createOfflineContent(offlineContent: OfflineContent): Single<Long>

    @Query("SELECT * FROM OfflineContent WHERE id = :contentId")
    fun searchOfflineContent(contentId: Int): Single<OfflineContent>

    @Query("SELECT * FROM OfflineContent WHERE isWishList = 1")
    fun getAllWishListContent(): Single<List<OfflineContent>>


    @Query("UPDATE OfflineContent SET isWishList = 1 WHERE id = :contentId")
    fun addWishListFlag(contentId: Int): Single<Int>

    @Query("UPDATE OfflineContent SET isWishList = 0 WHERE id = :contentId")
    fun removeWishListFlag(contentId: Int): Single<Int>

    @Query("UPDATE OfflineContent SET isWishList = 0")
    fun removeAllWishListFlag(): Single<Int>



}