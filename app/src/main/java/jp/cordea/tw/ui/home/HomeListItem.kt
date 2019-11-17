package jp.cordea.tw.ui.home

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.tw.R
import jp.cordea.tw.databinding.HomeListItemBinding

data class HomeListItemModel(
    val id: Long,
    val body: String
)

class HomeListItem(private val model: HomeListItemModel) : BindableItem<HomeListItemBinding>() {
    override fun getLayout(): Int = R.layout.home_list_item

    override fun bind(viewBinding: HomeListItemBinding, position: Int) {
        viewBinding.model = model
    }

    override fun equals(other: Any?): Boolean = model == other
}
