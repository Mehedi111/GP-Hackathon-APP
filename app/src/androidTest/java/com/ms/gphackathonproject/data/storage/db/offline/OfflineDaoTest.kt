package com.ms.gphackathonproject.data.storage.db.offline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.gm.lollipop.storage.db.download.OfflineContent
import com.gm.lollipop.storage.db.download.OfflineDaoAccess
import com.google.common.truth.Truth.assertThat
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.data.storage.db.AppDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

@ExperimentalCoroutinesApi
@SmallTest // tell junit that it is unit test
@HiltAndroidTest //inject dependency into this class
class OfflineDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule //tell junit to run synchronous (One function after another)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: OfflineDaoAccess

    @Before
    fun setup() {
        hiltRule.inject() //this function inject all our dependency here which is annotate with @inject
        dao = database.offlineDaoAccess()
    }

    @After // close db after complete
    fun dismiss() {
        database.close()
    }

    // Now test all the functionality of Dao

    // Run blocking -> coroutine will runn on main thread in test purpose
    //add to wishlist check
    @Test
    fun addToWishList() = runBlockingTest {
        val shoppingItem = OfflineContent(id = 1, isWishList = 1, content = Content())

        dao.createOfflineContent(shoppingItem).map {
            shoppingItem.id?.let { it1 -> dao.addWishListFlag(it1) }?.map {
                dao.getAllWishListContent().map {
                    assertThat(it).contains(shoppingItem)
                }
            }
        }

    }

    // Remove from wishlist check
    @Test
    fun removeFromWishList() = runBlockingTest {
        val shoppingItem = OfflineContent(id = 1, isWishList = 1, content = Content())

        dao.createOfflineContent(shoppingItem)
            .map {
                shoppingItem.id?.let { it1 -> dao.addWishListFlag(it1) }
            }

        dao.getAllWishListContent().map {
            assertThat(it).contains(shoppingItem)
        }

        shoppingItem.id?.let { dao.removeWishListFlag(it) }

        dao.getAllWishListContent().map {
            assertThat(it).doesNotContain(shoppingItem)
        }

    }

}