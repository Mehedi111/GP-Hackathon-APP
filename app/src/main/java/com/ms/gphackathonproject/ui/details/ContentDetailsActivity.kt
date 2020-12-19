package com.ms.gphackathonproject.ui.details

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.gm.lollipop.data.Resource
import com.gm.lollipop.utils.extras.Util
import com.gm.lollipop.utils.image.ImageLoader
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.R
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.data.model.details.ContentDetails
import com.ms.gphackathonproject.databinding.ActivityContentDetailsBinding
import com.ms.gphackathonproject.ui.BaseActivity
import com.ms.gphackathonproject.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@AndroidEntryPoint
class ContentDetailsActivity : BaseActivity(), View.OnClickListener {

    override fun parentLayout(): Int = R.id.parent_layout

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityContentDetailsBinding? = null
    private val binding get() = _binding!!

    private var content: Content? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityContentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParcelable()

        initViews()
        subscribeObserver()
        checkIfLoveAndWish()
    }

    private fun initViews() {
        val layoutParams = binding.imageCard.layoutParams
        binding.imageViewCard.setBackgroundResource(R.drawable.ic_bg_shape_detailsview)
        layoutParams.width = (Util.getScreenHeightWidth(this).first / 1.5).roundToInt()
        binding.imageCard.layoutParams = layoutParams
        ImageLoader.loadImage(
            binding.imageViewPreview,
            content?.poster_path,
            Constants.TYPE_MOVIE,
            R.drawable.default_potrait
        )
        ImageLoader.loadImage(
            binding.imageViewBackground,
            content?.poster_path,
            Constants.TYPE_MOVIE,
            R.drawable.default_potrait,
            blurEffectEnable = true
        )

        binding.videoTitleTxt.text = content?.title ?: content?.name ?: "Not found"
        binding.videoShortDesTxt.text = content?.overview


        binding.ivAddWishList.setOnClickListener(this)
    }

    /*
    * Check local db for favorite, downloads and wish list
    * */
    private fun checkIfLoveAndWish() {
        content?.id?.let {
            disposable?.add(offlineDaoAccess.searchOfflineContent(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        Timber.d("check_offline %s", data.isWishList)
                        makeWishListedView(data.isWishList == Constants.WISHLIST)

                    }, {
                        makeWishListedView(false)
                    }
                )
            )
        }
    }

    private fun makeWishListedView(wish: Boolean) {
        binding.ivAddWishList.isSelected = wish
    }

    private fun subscribeObserver() {
        viewModel.observeContentDetailsLiveData().observe(this, {
            if (it != null) {
                val resource = it.getContentIfNotHandled()
                when (resource?.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("onChanged: LOADING...")
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("onChanged: SUCCESS...")
                        if (resource.data != null) {
                            setDetailsToView(resource.data)
                        }
                    }
                    Resource.Status.ERROR -> {
                        showToast(resource.message)
                        Timber.e("onChanged: ERROR...%s", resource.message)
                    }
                }
            }
        })

        // request api
        content?.id?.let {
            viewModel.getContentDetailsLiveData(it, type ?: Constants.TYPE_MOVIE)
        }
    }

    private fun setDetailsToView(data: ContentDetails) {
        if (type == Constants.TYPE_MOVIE) {
            binding.videoDurationTxt.text = ("Status : " + data.status
                    + " • " + data.releaseDate + Util.getGenre(data.genres))
        } else {
            binding.videoDurationTxt.text = (
                    "Number of Episodes : " + data.numberOfEpisodes + "\n"
                            + "Number of Seasons : " + data.numberOfSeasons + "\n"
                            + "Last Air date: " + data.lastAirDate + Util.getGenre(data.genres)
                    )
        }

    }

    /*
    * get parcelable from parent activity
    * */
    private fun getParcelable() {
        content = intent.getParcelableExtra(Constants.CONTENT_EXTRA)
        type = intent.getStringExtra(Constants.CONTENT_TYPE)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("LifeState %s", "onDestroy")
        _binding = null
    }

    fun onBackButton(view: View) {
        onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivAddWishList -> {
                content?.let { content ->
                    disposable?.add(
                        Observable.timer(200, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                viewModel.changeWishListStatus(
                                    !binding.ivAddWishList.isSelected,
                                    content
                                )
                                binding.ivAddWishList.isSelected = !binding.ivAddWishList.isSelected

                                showSnackBar(
                                    binding.parentLayout,
                                    if (binding.ivAddWishList.isSelected) "Add to Wishlist" else "Remove from Wishlist"
                                )
                            }
                    )
                }
            }
        }
    }
}