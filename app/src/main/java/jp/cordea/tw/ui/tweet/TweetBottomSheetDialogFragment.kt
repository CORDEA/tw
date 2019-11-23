package jp.cordea.tw.ui.tweet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.cordea.tw.R
import jp.cordea.tw.ui.home.HomeFragment
import kotlinx.android.synthetic.main.tweet_bottom_sheet_dialog_fragment.*
import javax.inject.Inject

class TweetBottomSheetDialogFragment : BottomSheetDialogFragment() {
    interface OnTweetListener {
        fun onTweet(text: String)
    }

    @Inject
    lateinit var listener: OnTweetListener

    override fun onAttach(context: Context) {
        (requireParentFragment() as HomeFragment)
            .subcomponent
            .tweetBottomSheetDialogFragmentSubcomponent()
            .create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.tweet_bottom_sheet_dialog_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener { listener.onTweet(textInputLayout.editText!!.text.toString()) }
    }
}
