package com.gm.lollipop.storage.db.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.gm.lollipop.data.Resource
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.data.model.DiscoverContent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/10/2020.
 */

class OfflineContentRepository @Inject constructor(
    private val offlineDaoAccess: OfflineDaoAccess
) {

    /**
     * @param makeFav true/false for make and remove favorite
     * @param content content which need to perform action
     */
    fun changeWishListStatus(makeWish: Boolean, content: Content) {
        val offlineDownload = OfflineContent()
        val contentId = content.id
        offlineDownload.id = contentId
        offlineDownload.isWishList = if (makeWish) Constants.WISHLIST else Constants.NOT_WISHLIST
        offlineDownload.content = content

        contentId?.let { id ->
            offlineDaoAccess.searchOfflineContent(contentId = id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Timber.d("check_item : got data %s", id)
                    it
                }
                .flatMap {
                    if (it.isWishList == Constants.WISHLIST && !makeWish) {
                        offlineDaoAccess.removeWishListFlag(id)
                            .subscribeOn(Schedulers.io())
                            .map {
                                Timber.d("check_item : remove wish list %s ", it)
                            }
                    } else {
                        offlineDaoAccess.addWishListFlag(id)
                            .subscribeOn(Schedulers.io())
                            .map {
                                Timber.d("check_item : got and made wish list %s ", it)
                            }
                    }
                }
                .onErrorResumeNext {
                    offlineDaoAccess.createOfflineContent(offlineDownload)
                        .subscribeOn(Schedulers.io())
                        .map {
                            Timber.d("check_item : create and saved wish list offline %s", it)
                        }.onErrorReturn {
                            Timber.d("check_item : create offline error %s", it.message)
                        }
                }.subscribe()
        }
    }


    /**
     * get all wishlist from local db
     */
    fun getAllWishList(): LiveData<Resource<List<OfflineContent>>> {
        return LiveDataReactiveStreams.fromPublisher(offlineDaoAccess.getAllWishListContent()
            .subscribeOn(Schedulers.io())
            .map {
                Resource.success(it)
            }
            .onErrorReturn {
                    throwable -> Resource.error(throwable.message, null)
            }
            .toFlowable())
    }


}