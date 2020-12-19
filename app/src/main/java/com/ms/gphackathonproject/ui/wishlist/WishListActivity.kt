package com.ms.gphackathonproject.ui.wishlist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gm.lollipop.data.Resource
import com.gm.lollipop.storage.db.download.OfflineContent
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.R
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.databinding.ActivityWishListBinding
import com.ms.gphackathonproject.ui.BaseActivity
import com.ms.gphackathonproject.ui.adapter.HomeChildAdapter
import com.ms.gphackathonproject.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WishListActivity : BaseActivity() {

    override fun parentLayout(): Int = R.id.parent_layout

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityWishListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        subscribedObserver()
    }

    private fun initViews() {
        binding.actionBar.title.text = resources.getString(R.string.my_wishlist)
    }

    private fun subscribedObserver() {
        viewModel.observeOfflineLiveData().observe(this, {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged : loading")
                    }

                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged : success")
                        if (!it.data.isNullOrEmpty()) {
                            setUpRV(it.data)
                        }
                    }

                    Resource.Status.ERROR -> {
                        Timber.d("onChanged : error %s", it.message)
                    }

                }
            }
        })

        viewModel.getWishlistLiveData()
    }

    private fun setUpRV(it: List<OfflineContent>) {
        binding.notFound.parentLayout.visibility = View.GONE
        val dList = ArrayList<Content>()
        it.map {
            it.content?.let { it1 -> dList.add(it1) }
        }

        val layoutManager = GridLayoutManager(this, 2)
        binding.wishlistRV.layoutManager = layoutManager
        binding.wishlistRV.setHasFixedSize(true)

        binding.wishlistRV.adapter = HomeChildAdapter(this, Constants.TYPE_WISHLIST, dList)
    }


}