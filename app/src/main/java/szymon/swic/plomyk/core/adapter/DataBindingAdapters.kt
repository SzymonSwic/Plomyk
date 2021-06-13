package szymon.swic.plomyk.core.adapter

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("android:isInvisible")
fun setIsInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}
