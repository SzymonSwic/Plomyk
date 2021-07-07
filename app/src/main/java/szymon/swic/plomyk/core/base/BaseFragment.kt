package szymon.swic.plomyk.core.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: BaseViewModel>(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        bindViewModelToLifecycle()
    }

    open fun initViews() {}

    open fun initObservers() {
        observeMessage()
        observeUiState()
    }

    open fun onIdleState() {}

    open fun onPendingState() {}

    private fun showToast(it: String) = Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

    protected fun showToast(@StringRes stringRes: Int) = showToast(getString(stringRes))

    private fun bindViewModelToLifecycle() = lifecycle.addObserver(viewModel)

    private fun observeMessage() {
        viewModel.message.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                UIState.Idle -> onIdleState()
                UIState.Pending -> onPendingState()
            }
        }
    }
}
