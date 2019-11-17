package jp.cordea.tw.ui.home

import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class HomeAdapter : GroupAdapter<GroupieViewHolder>() {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeListItem>() {
            override fun areItemsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean =
                oldItem.isSameAs(newItem)

            override fun areContentsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean =
                oldItem == newItem
        }
    }

    private val differ = AsyncPagedListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: PagedList<HomeListItem>) {
        differ.submitList(list)
    }

    override fun getItem(position: Int): Item<*> = differ.getItem(position)!!

    override fun getItemCount(): Int = differ.itemCount
}
