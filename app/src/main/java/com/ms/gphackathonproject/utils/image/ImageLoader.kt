package com.gm.lollipop.utils.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.gm.lollipop.utils.extras.CharParser
import com.ms.gphackathonproject.R
import com.ms.gphackathonproject.utils.animation.BlurTransformation
import timber.log.Timber

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

object ImageLoader {

    /**
     * Invoke this function to load image on particular image view
     * @param view specific imageview
     * @param path url of image privided from api
     * @param type type of image
     * @param placeHolder placeholder of image
     * @blurEffectEnable enable blur effect for blurry image

     */
    fun loadImage(
        view: ImageView, path: String?, type: String?,
        placeHolder: Int? = null,
        blurEffectEnable: Boolean = false
    ) {
        if (path != null && type != null) {

            val imageUrl: String = CharParser.getImageUrlFromPath(path, type)

            Timber.e("ImageUrlAndType %s", "$imageUrl $type")

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.setColorSchemeColors(
                R.color.color_secondary,
                R.color.color_primary,
                R.color.purple_700
            )
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val options: RequestOptions = RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()

            if (placeHolder == null) {
                options.placeholder(circularProgressDrawable)
            } else {
                options.placeholder(placeHolder).error(placeHolder)
            }

            if (blurEffectEnable) {
                options.apply(RequestOptions.bitmapTransform(BlurTransformation()))
            }

            Glide.with(view.context.applicationContext)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(view)
        } else {
            Timber.e("skipping image")
        }
    }

}