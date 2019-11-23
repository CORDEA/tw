package jp.cordea.tw.ui.tweet

import dagger.Subcomponent

@Subcomponent
interface TweetBottomSheetDialogFragmentSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): TweetBottomSheetDialogFragmentSubcomponent
    }

    fun inject(fragment: TweetBottomSheetDialogFragment): TweetBottomSheetDialogFragment
}
