package szymon.swic.plomyk.features.splash.presentation

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment

class SplashScreenFragment : BaseFragment<SplashScreenViewModel>(R.layout.fragment_splash_screen) {

    override val viewModel: SplashScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onSplashDismissed()
    }
}
