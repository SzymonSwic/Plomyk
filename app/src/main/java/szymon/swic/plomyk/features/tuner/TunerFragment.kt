package szymon.swic.plomyk.features.tuner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.tuner_fragment.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.features.MainActivity
import szymon.swic.plomyk.features.songs.list.presentation.SongListFragment
import szymon.swic.plomyk.features.splash.SplashScreenFragment


class TunerFragment : BaseFragment<TunerViewModel>(R.layout.tuner_fragment) {

    override val viewModel: TunerViewModel by viewModel()
    private var permissionToRecordGranted = false
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        handlePermission()
        setFrequencyObserver()
    }

    private fun handlePermission() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordGranted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordGranted) (activity as MainActivity).replaceFragment(
            get<SongListFragment>(), addToBackStack = true
        )
    }

    override fun initViews() {
        super.initViews()
        activity?.title = resources.getString(R.string.title_tuner)
        button_start.setOnClickListener { viewModel.startAnalysing() }
        button_stop.setOnClickListener { viewModel.stopAnalysing() }
    }

    private fun setFrequencyObserver() {

        val frequencyObserver = Observer<Double> {
            text_curr_frequency.text = "$it Hz"
        }

        viewModel.frequency.observe(viewLifecycleOwner, frequencyObserver)
    }

}
