package com.ms.gphackathonproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.gm.lollipop.data.Resource
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.R
import com.ms.gphackathonproject.databinding.ActivityMainBinding
import com.ms.gphackathonproject.ui.BaseActivity
import com.ms.gphackathonproject.ui.adapter.HomeParentAdapter
import com.ms.gphackathonproject.ui.viewmodel.MainViewModel
import com.ms.gphackathonproject.ui.wishlist.WishListActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity(), View.OnClickListener {

    override fun parentLayout(): Int = R.id.parent_layout

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var adapter: HomeParentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        subscribeObserver()
        subsribeOfflineContenObserver()
        getContentFromAPI()
    }



    private fun initViews() {
        //just set empty adapter
        adapter = HomeParentAdapter(this)
        binding.homePrentRV.adapter = adapter

        binding.actionBar.wishlist.visibility = View.VISIBLE
        binding.actionBar.wishlist.setOnClickListener(this)

        binding.actionBar.title.text = resources.getString(R.string.discover)
    }

    //request api for content
    private fun getContentFromAPI() {
        viewModel.getMovies(1)
    }

    /*
    * Subscribe all the observer
    * */
    private fun subscribeObserver() {
        viewModel.observeMoviesLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert movie patch
                            resource.data.contentType = Constants.TYPE_MOVIE
                            adapter?.insertNewPatch(resource.data)

                            viewModel.getTvShows(1)
                        }
                    }
                    Resource.Status.ERROR -> {
                        viewModel.getOfflineMovies()
                        showToast(resource.message)
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })

        viewModel.observeTVLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert tv show patch
                            resource.data.contentType = Constants.TYPE_TV
                            adapter?.insertNewPatch(resource.data)

                            viewModel.getTrendingLiveData()
                        }
                    }
                    Resource.Status.ERROR -> {
                        viewModel.getOfflineTVShows()
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })

        viewModel.observeTrendingLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert trending items patch
                            resource.data.contentType = Constants.TYPE_TRENDING
                            adapter?.insertNewPatch(resource.data)
                        }
                    }
                    Resource.Status.ERROR -> {
                        viewModel.getOfflineTrending()
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })
    }

    private fun subsribeOfflineContenObserver() {
        viewModel.observeOfflineMovieLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert movie patch
                            resource.data.contentType = Constants.TYPE_MOVIE
                            adapter?.insertNewPatch(resource.data)

                            viewModel.getTvShows(1)
                        }
                    }
                    Resource.Status.ERROR -> {
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })

        viewModel.observeOfflineTVLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert tv show patch
                            resource.data.contentType = Constants.TYPE_TV
                            adapter?.insertNewPatch(resource.data)

                            viewModel.getTrendingLiveData()
                        }
                    }
                    Resource.Status.ERROR -> {
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })

        viewModel.observeOfflineTrendingLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            //insert trending items patch
                            resource.data.contentType = Constants.TYPE_TRENDING
                            adapter?.insertNewPatch(resource.data)
                        }
                    }
                    Resource.Status.ERROR -> {
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.wishlist -> {
                val intent = Intent(this, WishListActivity::class.java)
                startActivity(intent)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("LifeState %s", "onDestroy")
        _binding = null
        adapter = null
    }

}