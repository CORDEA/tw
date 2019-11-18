package jp.cordea.tw.ui.home

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.tw.R
import jp.cordea.tw.databinding.HomeBottomSheetItemBinding

class HomeBottomSheetItemModel(val title: String)

class HomeBottomSheetItem(
    private val model: HomeBottomSheetItemModel
) : BindableItem<HomeBottomSheetItemBinding>() {
    override fun getLayout(): Int = R.layout.home_bottom_sheet_item

    override fun bind(viewBinding: HomeBottomSheetItemBinding, position: Int) {
        viewBinding.model = model
    }
}
