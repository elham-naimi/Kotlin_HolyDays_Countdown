package com.elna.kotlinfragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.elna.model.HolyDay
import com.elna.ui.HolyDayListAdapter
import com.elna.viewmodel.HolyDayViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private lateinit var holyDayViewModel: HolyDayViewModel
    private lateinit var adapter: HolyDayListAdapter
    private var twoPane: Boolean = false
    private var TAG = ItemListActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        holyDayViewModel = ViewModelProviders.of(this).get(HolyDayViewModel::class.java)
        setSupportActionBar(toolbar)
        toolbar.title = title

        val onClickListener: View.OnClickListener

        onClickListener = View.OnClickListener { v ->
            val item = v.tag as HolyDay
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.holyDayName)
                    }
                }
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.holyDayName)
                }
                v.context.startActivity(intent)
            }
        }

        adapter = HolyDayListAdapter(this, onClickListener)



        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        item_list.adapter = adapter;

    }

    override fun onStart() {
        super.onStart()
        holyDayViewModel.loadHolyDays()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ result -> onSuccess(result) },
                { failure -> onFailed(failure) })

    }

    private fun onFailed(failure: Throwable) {
        Log.i(TAG, "OnFailed")
        Log.i(TAG, "OnFailed"+failure.localizedMessage)
    }

    private fun onSuccess(result: ArrayList<HolyDay>) {
        Log.i(TAG, "OnSuccess"+result.size)
        if (result != null) {
            Log.i(TAG, "OnSuccess" + result)
                    adapter.setWords(result)
        }
    }

}

