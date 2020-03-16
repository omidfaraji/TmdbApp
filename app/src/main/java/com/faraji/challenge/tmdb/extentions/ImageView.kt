package  com.faraji.challenge.tmdb.extentions

import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import jp.wasabeef.picasso.transformations.CropCircleTransformation

fun ImageView.load(
    path: String?,
    @DrawableRes placeholder: Int = -1,
    customizeBlock: (RequestCreator.() -> Unit)? = null,
    callBack: (() -> Unit)? = null
) {
    var fullPath = path

    if (path.isNullOrBlank()) {
        return
    }

    if (!path.startsWith("http", true))
        fullPath = "file://$path"

    val picasso = Picasso.get().load(fullPath)
    if (placeholder != -1)
        picasso.placeholder(placeholder)

    customizeBlock?.invoke(picasso)

    picasso.into(this, object : Callback {
        override fun onSuccess() {
            Log.i("onSuccess: %s", fullPath)
            callBack?.invoke()

        }

        override fun onError(e: java.lang.Exception?) {
            Log.i("onError: %s", fullPath)
            e?.printStackTrace()
        }
    })
}


fun ImageView.loadWithCircularCrop(path: String?, @DrawableRes placeholder: Int? = null) {
    this.scaleType = ImageView.ScaleType.CENTER_CROP

    if (path.isNullOrBlank()) {
        return
    }
    var fullPath = path

    if (!path.startsWith("http", true))
        fullPath = "file://$path"

    val picasso = Picasso.get().load(fullPath)
        .apply { placeholder?.let { placeholder(it) } }
        .transform(CropCircleTransformation())
    try {
        picasso.into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

