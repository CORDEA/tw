package jp.cordea.tw.ui.home

import androidx.core.view.isVisible
import coil.api.load
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.internal.TweetMediaUtils
import com.xwray.groupie.databinding.BindableItem
import jp.cordea.tw.R
import jp.cordea.tw.databinding.HomeListItemBinding

data class HomeListItemModel(
    val id: Long,
    val body: String,
    val images: List<String>
) {
    companion object {
        fun from(tweet: Tweet) = HomeListItemModel(
            tweet.id,
            tweet.text,
            tweet.entities.media
                .filter { it.type == TweetMediaUtils.PHOTO_TYPE }
                .map { it.mediaUrlHttps }
        )
    }

    val shouldShowImages = images.isNotEmpty()
    val shouldShowSubImages = images.size > 2
}

class HomeListItem(private val model: HomeListItemModel) : BindableItem<HomeListItemBinding>() {
    override fun getId(): Long = model.id
    override fun getLayout(): Int = R.layout.home_list_item

    override fun bind(viewBinding: HomeListItemBinding, position: Int) {
        viewBinding.model = model
        viewBinding.image.isVisible = model.shouldShowImages

        if (model.shouldShowImages) {
            viewBinding.image1.load(model.images[0])
            viewBinding.image2.isVisible = model.shouldShowSubImages
            viewBinding.image3.isVisible = model.shouldShowSubImages
            if (model.shouldShowSubImages) {
                viewBinding.image2.load(model.images[1])
                viewBinding.image3.load(model.images[2])
            }
        }
    }

    override fun equals(other: Any?): Boolean = model == other
    override fun hashCode(): Int = model.hashCode()
}
