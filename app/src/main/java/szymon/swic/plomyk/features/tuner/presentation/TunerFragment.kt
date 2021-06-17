package szymon.swic.plomyk.features.tuner.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.tuner_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.core.permissions.PermissionType
import szymon.swic.plomyk.core.permissions.PermissionsHelper


class TunerFragment : BaseFragment<TunerViewModel>(R.layout.tuner_fragment) {

    override val viewModel: TunerViewModel by viewModel()
    private var permissionToRecordGranted = false

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
            val type = PermissionType.RECORD_AUDIO
            requestPermissions(type.permissions, type.requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordGranted = requestCode == PermissionType.RECORD_AUDIO.requestCode &&
            PermissionsHelper.hasPermissionBeenGranted(grantResults)

        if (!permissionToRecordGranted) viewModel.goBack()
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
