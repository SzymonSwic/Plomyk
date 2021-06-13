package szymon.swic.plomyk.core.extensions

import android.animation.LayoutTransition
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import szymon.swic.plomyk.R

fun Menu.setupSearchView(onQueryTextChange: ((String?) -> Unit)) {
    val searchItem = this.findItem(R.id.action_search)
    val searchView: SearchView = searchItem.actionView as SearchView
    searchView.maxWidth = Integer.MAX_VALUE

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newQueryText: String?): Boolean {
            onQueryTextChange.invoke(newQueryText)
            return false
        }
    })
    val searchBar = searchView.findViewById<View>(R.id.search_bar) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}
