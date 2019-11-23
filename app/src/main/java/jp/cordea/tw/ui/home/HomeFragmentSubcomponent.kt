package jp.cordea.tw.ui.home

import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import jp.cordea.tw.ui.tweet.TweetBottomSheetDialogFragment
import jp.cordea.tw.ui.tweet.TweetBottomSheetDialogFragmentSubcomponent

@Subcomponent(
    modules = [
        HomeFragmentBindModule::class
    ]
)
interface HomeFragmentSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: HomeFragment): HomeFragmentSubcomponent
    }

    fun inject(fragment: HomeFragment): HomeFragment

    fun tweetBottomSheetDialogFragmentSubcomponent(
    ): TweetBottomSheetDialogFragmentSubcomponent.Factory
}

@Module
interface HomeFragmentBindModule {
    @Binds
    fun bindOnItemClickListener(fragment: HomeFragment): HomeListItem.OnItemClickListener
}
